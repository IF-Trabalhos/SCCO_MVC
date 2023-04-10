package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ProcedimentoDTO;
import com.example.SCCO_MVC.model.entity.Procedimento;
import com.example.SCCO_MVC.service.ProcedimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/procedimentos")
@RequiredArgsConstructor
public class ProcedimentoController {
    private final ProcedimentoService service;

    @GetMapping
    public ResponseEntity get(){
        List<Procedimento> procedimentos = service.getProcedimentos();
        return ResponseEntity.ok(procedimentos.stream().map(ProcedimentoDTO::create).collect(Collectors.toList()));
    }
}
