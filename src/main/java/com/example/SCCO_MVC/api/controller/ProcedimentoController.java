package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ProcedimentoDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.*;
import com.example.SCCO_MVC.service.EspecialidadeService;
import com.example.SCCO_MVC.service.ProcedimentoService;
import com.example.SCCO_MVC.service.TratamentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/procedimentos")
@RequiredArgsConstructor
public class ProcedimentoController {
    private final ProcedimentoService service;
    private final TratamentoService tratamentoService;
    private final EspecialidadeService especialidadeService;

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

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Procedimento> procedimento = service.getProcedimentoById(id);
        if (!procedimento.isPresent()) {
            return new ResponseEntity("Procedimento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(procedimento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity post(@RequestBody ProcedimentoDTO dto){
        try{
            Procedimento procedimento = converter(dto);
            procedimento = service.salvar(procedimento);
            return new ResponseEntity(procedimento, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProcedimentoDTO dto) {
        if (!service.getProcedimentoById(id).isPresent()) {
            return new ResponseEntity("Procedimento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Procedimento procedimento = converter(dto);
            procedimento.setId(id);
            service.salvar(procedimento);
            return ResponseEntity.ok(procedimento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Procedimento converter (ProcedimentoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Procedimento procedimento = modelMapper.map(dto, Procedimento.class);
        if (dto.getTratamentoId() != null) {
            Optional<Tratamento> tratamento = tratamentoService.getTratamentoById
                    (dto.getTratamentoId());
            if (!tratamento.isPresent()) {
                procedimento.setTratamento(null);
            } else {
                procedimento.setTratamento(tratamento.get());
            }
        }
        if (dto.getEspecialidadeId() != null) {
            Optional<Especialidade> especialidade = especialidadeService.getEspecialidadeById(dto.getEspecialidadeId());
            if (!especialidade.isPresent()) {
                procedimento.setEspecialidade(null);
            } else {
                procedimento.setEspecialidade(especialidade.get());
            }
        }
        return procedimento;
    }
}
