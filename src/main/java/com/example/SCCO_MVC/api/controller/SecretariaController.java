package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.SecretariaDTO;
import com.example.SCCO_MVC.model.entity.Secretaria;
import com.example.SCCO_MVC.service.EnderecoService;
import com.example.SCCO_MVC.service.SecretariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/secretaria")
@RequiredArgsConstructor
public class SecretariaController {
    private final SecretariaService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get(){
        List<Secretaria> secretarias = service.getSecretarias();
        return ResponseEntity.ok(secretarias.stream().map(SecretariaDTO::create).collect(Collectors.toList()));
    }

}
