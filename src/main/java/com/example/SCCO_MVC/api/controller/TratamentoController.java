package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.TratamentoDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.model.entity.Tratamento;
import com.example.SCCO_MVC.service.TratamentoService;
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
@RequestMapping("api/v1/tratamentos")
@RequiredArgsConstructor
public class TratamentoController {
    private final TratamentoService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de tratamentos no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de tratamentos retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de tratamentos")
    })
    public ResponseEntity get(){
        List<Tratamento> tratamentos = service.getTratamentos();
        return ResponseEntity.ok(tratamentos.stream().map(TratamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um tratamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Tratamento encontrado"),
            @ApiResponse(code = 404, message = "Tratamento não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Tratamento") Long id){
        Optional<Tratamento> tratamentos = service.getTratamentoById(id);
        if (!tratamentos.isPresent()){
            return  new ResponseEntity("Tratamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tratamentos.map(TratamentoDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar novo tratamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Tratamento cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar tratamento")
    })
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
    @ApiOperation("Atualiza Tratamento")
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
    @DeleteMapping("{id}")
    @ApiOperation("Exclui um tratamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Tratamento excluido com sucesso"),
            @ApiResponse(code = 404, message = "Tratamento não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Tratamento") Long id){
        Optional<Tratamento> tratamento = service.getTratamentoById(id);
        if (!tratamento.isPresent()) {
            return new ResponseEntity("Tratamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(tratamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Tratamento converter (TratamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Tratamento tratamento = modelMapper.map(dto, Tratamento.class);
        return tratamento;
    }
}
