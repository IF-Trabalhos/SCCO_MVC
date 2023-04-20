package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.api.dto.DisponibilidadeDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Disponibilidade;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.service.DisponibilidadeService;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/v1/disponibilidades")
@RequiredArgsConstructor

public class DisponibilidadeController {
    private final DisponibilidadeService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Disponibilidade> disponibilidades= service.getDisponibilidades();
        return ResponseEntity.ok(disponibilidades.stream().map(DisponibilidadeDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Disponibilidade> disponibilidade = service.getDisponibilidadeById(id);
        if (!disponibilidade.isPresent()){
            return  new ResponseEntity("Disponibilidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(disponibilidade.map(DisponibilidadeDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody DisponibilidadeDTO dto){
        try{
            Disponibilidade disponibilidade = converter(dto);
            disponibilidade = service.salvar(disponibilidade);
            return new ResponseEntity(disponibilidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Disponibilidade converter(DisponibilidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Disponibilidade disponibilidade = modelMapper.map(dto, Disponibilidade.class);
        return disponibilidade;
    }
}
