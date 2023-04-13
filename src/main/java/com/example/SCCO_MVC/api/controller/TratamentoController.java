package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.api.dto.TratamentoDTO;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Tratamento;
import com.example.SCCO_MVC.service.TratamentoService;
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
@RequestMapping("api/v1/tratamentos")
@RequiredArgsConstructor
public class TratamentoController {
    private final TratamentoService service;

    @GetMapping
    public ResponseEntity get(){
        List<Tratamento> tratamentos = service.getTratamentos();
        return ResponseEntity.ok(tratamentos.stream().map(TratamentoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Tratamento> tratamentos = service.getTratamentoById(id);
        if (!tratamentos.isPresent()){
            return  new ResponseEntity("Tratamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tratamentos.map(TratamentoDTO::create));

    }
}
