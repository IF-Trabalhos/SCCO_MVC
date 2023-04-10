package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ConsultaDTO;
import com.example.SCCO_MVC.model.entity.Consulta;
import com.example.SCCO_MVC.service.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/consultas")
@RequiredArgsConstructor
public class ConsultaController {
    private final ConsultaService service;

    @GetMapping
    public ResponseEntity get(){
        List<Consulta> consultas = service.getConsultas();
        return ResponseEntity.ok(consultas.stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }
}