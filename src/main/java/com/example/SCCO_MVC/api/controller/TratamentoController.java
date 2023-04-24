package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.TratamentoDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Tratamento;
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

    @PostMapping
    public ResponseEntity post(@RequestBody TratamentoDTO dto){
        try{
            Tratamento tratamento = converter(dto);
            tratamento = service.salvar(tratamento);
            return new ResponseEntity(tratamento, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody TratamentoDTO dto) {
        if (!service.getTratamentoById(id).isPresent()) {
            return new ResponseEntity("Tratamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Tratamento tratamento = converter(dto);
            tratamento.setId(id);
            service.salvar(tratamento);
            return ResponseEntity.ok(tratamento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Tratamento converter (TratamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Tratamento tratamento = modelMapper.map(dto, Tratamento.class);
        return tratamento;
    }
}
