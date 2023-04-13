package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.AgendaDTO;
import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.model.entity.Agenda;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.service.AgendaService;

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
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor

public class AgendaController {
    private final AgendaService service;
    @GetMapping()
    public ResponseEntity get(){
        List<Agenda> agendas = service.getAgendas();
        return ResponseEntity.ok(agendas.stream().map(AgendaDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Agenda> agendas = service.getAgendaById(id);
        if (!agendas.isPresent()){
            return  new ResponseEntity("Agenda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(agendas.map(AgendaDTO::create));

    }
}
