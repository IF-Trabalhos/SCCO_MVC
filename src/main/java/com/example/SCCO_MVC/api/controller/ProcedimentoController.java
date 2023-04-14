package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ProcedimentoDTO;
import com.example.SCCO_MVC.model.entity.Procedimento;
import com.example.SCCO_MVC.service.ProcedimentoService;
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
@RequestMapping("api/v1/procedimentos")
@RequiredArgsConstructor
public class ProcedimentoController {
    private final ProcedimentoService service;

    @GetMapping
    public ResponseEntity get(){
        List<Procedimento> procedimentos = service.getProcedimentos();
        return ResponseEntity.ok(procedimentos.stream().map(ProcedimentoDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Procedimento> procedimentos = service.getProcedimentoById(id);
        if (!procedimentos.isPresent()){
            return  new ResponseEntity("Procedimento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(procedimentos.map(ProcedimentoDTO::create));

    }
}
