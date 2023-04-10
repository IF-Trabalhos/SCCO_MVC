package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.service.DentistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}
