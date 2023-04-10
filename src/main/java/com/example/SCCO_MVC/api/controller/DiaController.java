package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.DiaDTO;
import com.example.SCCO_MVC.model.entity.Dia;
import com.example.SCCO_MVC.service.DiaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}
