package com.example.SCCO_MVC.controller;

import com.example.SCCO_MVC.dto.ExpedienteDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.ExpedienteService;
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
    public ResponseEntity<List<ExpedienteDTO>> get(){
        List<ExpedienteDTO> expedientes = service.get();
        return ResponseEntity.ok(expedientes);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Expediente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Expediente encontrado"),
            @ApiResponse(code = 404, message = "Expediente não encontrado")
    })
    public ResponseEntity<ExpedienteDTO> get(@PathVariable("id") Long id){
        ExpedienteDTO expediente = service.get(id);
        if (expediente == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(expediente);
    }

    @PostMapping
    @ApiOperation("Cadastrar nova expediente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Expediente cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar expediente")
    })
    public ResponseEntity<?> post(@RequestBody ExpedienteDTO dto){
        try{
            ExpedienteDTO expediente = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(expediente);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Expediente")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ExpedienteDTO dto) {
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
    @ApiOperation("Exclui uma Expediente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Expediente excluido com sucesso"),
            @ApiResponse(code = 404, message = "Expediente não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Expediente") Long id) {
        ExpedienteDTO expediente = service.get(id);
        if (expediente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Expediente não encontrada");
        }
        try {
            service.delete(expediente);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}