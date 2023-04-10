package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.AgendaDTO;
import com.example.SCCO_MVC.model.entity.Agenda;
import com.example.SCCO_MVC.service.AgendaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor

public class AgendaController {
    private final AgendaService service;
    @GetMapping()
    public ResponseEntity get(){
        List<Agenda> agendas = service.getAgendas();
        return ResponseEntity.ok(agendas.stream().map(AgendaDTO::create).collect(Collectors.toList()));
    }
}
