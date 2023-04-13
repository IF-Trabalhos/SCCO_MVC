package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.api.dto.SecretariaDTO;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Secretaria;
import com.example.SCCO_MVC.service.EnderecoService;
import com.example.SCCO_MVC.service.SecretariaService;
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
@RequestMapping("/api/v1/secretarias")
@RequiredArgsConstructor
public class SecretariaController {
    private final SecretariaService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get(){
        List<Secretaria> secretarias = service.getSecretarias();
        return ResponseEntity.ok(secretarias.stream().map(SecretariaDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Secretaria> secretarias = service.getSecretariaById(id);
        if (!secretarias.isPresent()){
            return  new ResponseEntity("Secretaria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(secretarias.map(SecretariaDTO::create));

    }

}
