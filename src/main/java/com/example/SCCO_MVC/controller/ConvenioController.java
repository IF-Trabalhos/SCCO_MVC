package com.example.SCCO_MVC.controller;


import com.example.SCCO_MVC.dto.ConvenioDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.ConvenioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/convenios")
@RequiredArgsConstructor
@CrossOrigin
public class ConvenioController {
    private final ConvenioService service;

    @GetMapping
    @ApiOperation("Retorna a lista de convenios no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de convenios retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de convenios")
    })
    public ResponseEntity<List<ConvenioDTO>> get(){
        List<ConvenioDTO> convenios = service.get();
        return ResponseEntity.ok(convenios);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Convenio")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Convenio encontrado"),
            @ApiResponse(code = 404, message = "Convenio não encontrado")
    })
    public ResponseEntity<ConvenioDTO> get(@PathVariable("id") @ApiParam("Id do Convenio") Long id){
        ConvenioDTO convenio = service.get(id);
        if (convenio == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convenio);
    }

    @PostMapping
    @ApiOperation("Cadastrar novo Convenio")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Convenio cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar o Convenio")
    })
    public ResponseEntity<?> post(@RequestBody ConvenioDTO dto){
        try{
            ConvenioDTO convenio = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(convenio);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza Convenio")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ConvenioDTO dto) {
        if (service.get(id) == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            service.save(dto);
            return ResponseEntity.ok(dto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Convenio")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Convenio excluido com sucesso"),
            @ApiResponse(code = 404, message = "Convenio não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Convenio") Long id) {
        ConvenioDTO convenio = service.get(id);
        if (convenio == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Convenio não encontrada");
        }
        try {
            service.delete(convenio);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}