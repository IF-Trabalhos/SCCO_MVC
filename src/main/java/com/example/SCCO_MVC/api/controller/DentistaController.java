package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.service.DentistaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/dentistas")
@RequiredArgsConstructor
public class DentistaController {
    private final DentistaService service;

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
    public Dentista converter(DentistaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Dentista dentista = modelMapper.map(dto, Dentista.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        dentista.setEndereco(endereco);
        return dentista;
    }
}
