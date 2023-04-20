package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.DiaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.*;
import com.example.SCCO_MVC.service.DiaService;

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
@RequestMapping("/api/v1/dias")
@RequiredArgsConstructor

public class DiaController {
    private final DiaService service;
    private final DisponibilidadeService disponibilidadeService;
    @GetMapping()
    public ResponseEntity get(){
        List<Dia> dias = service.getDias();
        return ResponseEntity.ok(dias.stream().map(DiaDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Dia> dias = service.getDiaById(id);
        if (!dias.isPresent()){
            return  new ResponseEntity("Dia não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dias.map(DiaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody DiaDTO dto){
        try{
            Dia dia = converter(dto);
            dia = service.salvar(dia);
            return new ResponseEntity(dia, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Dia converter(DiaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Dia dia = modelMapper.map(dto, Dia.class);
        if (dto.getDisponibilidadeId() != null) {
            Optional<Disponibilidade> disponibilidade = disponibilidadeService.getDisponibilidadeById
                    (dto.getDisponibilidadeId());
            if (!disponibilidade.isPresent()) {
                dia.setDisponibilidade(null);
            } else {
                dia.setDisponibilidade(disponibilidade.get());
            }
        }
        return dia;
    }
}
