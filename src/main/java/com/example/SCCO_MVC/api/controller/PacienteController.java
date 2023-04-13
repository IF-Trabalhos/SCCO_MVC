package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.api.dto.PacienteDTO;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.service.PacienteService;
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
@RequestMapping("api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService service;

    @GetMapping
    public ResponseEntity get(){
        List<Paciente> pacientes = service.getPacientes();
        return ResponseEntity.ok(pacientes.stream().map(PacienteDTO::create).collect(Collectors.toList()));
    }
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Paciente> pacientes = service.getPacienteById(id);
        if (!pacientes.isPresent()){
            return  new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pacientes.map(PacienteDTO::create));

    }
}
