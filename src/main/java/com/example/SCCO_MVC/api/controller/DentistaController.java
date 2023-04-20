package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.service.DentistaService;
import com.example.SCCO_MVC.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/dentistas")
@RequiredArgsConstructor
public class DentistaController {
    private final DentistaService service;
    private final EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity get(){
        List<Dentista> dentistas = service.getDentistas();
        return ResponseEntity.ok(dentistas.stream().map(DentistaDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Dentista> dentistas = service.getDentistaById(id);
        if (!dentistas.isPresent()){
            return  new ResponseEntity("Dentista não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dentistas.map(DentistaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody DentistaDTO dto){
        try{
            Dentista dentista = converter(dto);
            Endereco endereco = enderecoService.salvar(dentista.getEndereco());
            dentista.setEndereco(endereco);
            dentista = service.salvar(dentista);
            return new ResponseEntity(dentista, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Dentista converter(DentistaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Dentista dentista = modelMapper.map(dto, Dentista.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        dentista.setEndereco(endereco);
        return dentista;
    }
}
