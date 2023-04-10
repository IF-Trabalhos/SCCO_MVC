package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.DisponibilidadeDTO;
import com.example.SCCO_MVC.model.entity.Disponibilidade;
import com.example.SCCO_MVC.service.DisponibilidadeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}
