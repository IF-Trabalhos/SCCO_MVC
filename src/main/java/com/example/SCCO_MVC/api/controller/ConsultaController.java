package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ConsultaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.*;
import com.example.SCCO_MVC.service.ConsultaService;
import com.example.SCCO_MVC.service.DentistaService;
import com.example.SCCO_MVC.service.PacienteService;
import com.example.SCCO_MVC.service.TratamentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/consultas")
@RequiredArgsConstructor
public class ConsultaController {
    private final ConsultaService service;
    private final TratamentoService tratamentoService;
    private final DentistaService dentistaService;
    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity get(){
        List<Consulta> consultas = service.getConsultas();
        return ResponseEntity.ok(consultas.stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Consulta> consultas= service.getConsultaById(id);
        if (!consultas.isPresent()){
            return  new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(consultas.map(ConsultaDTO::create));
    }

    @PostMapping
    public ResponseEntity post(@RequestBody ConsultaDTO dto){
        try{
            Consulta consulta = converter(dto);
            consulta = service.salvar(consulta);
            return new ResponseEntity(consulta, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Consulta> consulta = service.getConsultaById(id);
        if (!consulta.isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(consulta.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/dentista/{id}")
    public ResponseEntity getConsultasByDentistaId(@PathVariable("id") Long id){
        Optional<Consulta> consultas = service.getConsultaByDentistaId(id);
        if (!consultas.isPresent()){
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(consultas.map(ConsultaDTO::create));
    }
    public  ResponseEntity getConsultasByData(@PathVariable("Data") Date data){
        Optional<Consulta> consultas = service.getConsultaByData(data);
        if(!consultas.isPresent()){
            return new ResponseEntity("Consulta não encontrada",HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.ok(consultas.map(ConsultaDTO::create));
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ConsultaDTO dto) {
        if (!service.getConsultaById(id).isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Consulta consulta = converter(dto);
            consulta.setId(id);
            service.salvar(consulta);
            return ResponseEntity.ok(consulta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Consulta converter (ConsultaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Consulta consulta = modelMapper.map(dto, Consulta.class);
        if (dto.getTratamentoId() != null) {
            Optional<Tratamento> tratamento = tratamentoService.getTratamentoById
                    (dto.getTratamentoId());
            if (!tratamento.isPresent()) {
                consulta.setTratamento(null);
            } else {
                consulta.setTratamento(tratamento.get());
            }
        }
        if (dto.getDentistaId() != null) {
            Optional<Dentista> dentista = dentistaService.getDentistaById(dto.getDentistaId());
            if (!dentista.isPresent()) {
                consulta.setDentista(null);
            } else {
                consulta.setDentista(dentista.get());
            }
        }
        if (dto.getPacienteId() != null) {
            Optional<Paciente> paciente = pacienteService.getPacienteById(dto.getPacienteId());
            if (!paciente.isPresent()) {
                consulta.setPaciente(null);
            } else {
                consulta.setPaciente(paciente.get());
            }
        }
        return consulta;
    }
}
