package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.ExpedienteDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Expediente;
import com.example.SCCO_MVC.service.ExpedienteService;
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
@RequestMapping("api/v1/expedientes")
@RequiredArgsConstructor
@CrossOrigin
public class ExpedienteController {
    private final ExpedienteService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de Expediente no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de Expediente retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de Expediente")
    })
    public ResponseEntity get(){
        List<Expediente> expedientes = service.getExpedientes();
        return ResponseEntity.ok(expedientes.stream().map(ExpedienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Expediente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Expediente encontrado"),
            @ApiResponse(code = 404, message = "Expediente não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Paciente") Long id){
        Optional<Expediente> expedientes = service.getExpedienteById(id);
        if (!expedientes.isPresent()){
            return  new ResponseEntity("Expediente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(expedientes.map(ExpedienteDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar nova expediente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Expediente cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar expediente")
    })
    public ResponseEntity post(@RequestBody ExpedienteDTO dto){
        try{
            Expediente expediente = converter(dto);
            expediente = service.salvar(expediente);
            return new ResponseEntity(expediente, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Expediente")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ExpedienteDTO dto) {
        if (!service.getExpedienteById(id).isPresent()) {
            return new ResponseEntity("Expediente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Expediente expediente = converter(dto);
            expediente.setId(id);
            service.salvar(expediente);
            return ResponseEntity.ok(expediente);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma Expediente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Expediente excluido com sucesso"),
            @ApiResponse(code = 404, message = "Expediente não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Expediente") Long id) {
        Optional<Expediente> expediente = service.getExpedienteById(id);
        if (!expediente.isPresent()) {
            return new ResponseEntity("Expediente não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(expediente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Expediente converter(ExpedienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Expediente expediente = modelMapper.map(dto, Expediente.class);
        return expediente;
    }
}