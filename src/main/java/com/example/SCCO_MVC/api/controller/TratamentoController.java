package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.TratamentoDTO;
import com.example.SCCO_MVC.model.entity.Tratamento;
import com.example.SCCO_MVC.service.TratamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}
