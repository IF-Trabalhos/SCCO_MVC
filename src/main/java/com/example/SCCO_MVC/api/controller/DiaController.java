package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.DiaDTO;
import com.example.SCCO_MVC.model.entity.Dia;
import com.example.SCCO_MVC.service.DiaService;

import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/dias")
@RequiredArgsConstructor

public class DiaController {
    private final DiaService service;
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
}
