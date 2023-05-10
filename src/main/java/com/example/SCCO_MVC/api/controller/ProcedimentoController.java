package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ProcedimentoDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.*;
import com.example.SCCO_MVC.service.EspecialidadeService;
import com.example.SCCO_MVC.service.ProcedimentoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation("Retorna a lista de procedimentos no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de procedimentos retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de procedimentos")
    })
    public ResponseEntity get(){
        List<Procedimento> procedimentos = service.getProcedimentos();
        return ResponseEntity.ok(procedimentos.stream().map(ProcedimentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um procedimentos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Procedimentos encontrado"),
            @ApiResponse(code = 404, message = "Procedimentos não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Procedimento") Long id){
        Optional<Procedimento> procedimentos = service.getProcedimentoById(id);
        if (!procedimentos.isPresent()){
            return  new ResponseEntity("Procedimento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(procedimentos.map(ProcedimentoDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar novo Procedimento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Procedimento cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar procedimento")
    })
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
    @ApiOperation("Atualiza um Procedimento")
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
    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Procedimento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Procedimento excluido com sucesso"),
            @ApiResponse(code = 404, message = "Procedimento não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Procedimento") Long id) {
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
