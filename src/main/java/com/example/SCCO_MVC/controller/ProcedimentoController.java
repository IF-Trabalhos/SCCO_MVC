package com.example.SCCO_MVC.controller;


import com.example.SCCO_MVC.dto.ProcedimentoDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.ProcedimentoService;
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
@RequestMapping("api/v1/procedimentos")
@RequiredArgsConstructor
@CrossOrigin
public class ProcedimentoController {
    private final ProcedimentoService service;

    @GetMapping
    @ApiOperation("Retorna a lista de procedimentos no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de procedimentos retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de procedimentos")
    })
    public ResponseEntity<List<ProcedimentoDTO>> get(){
        List<ProcedimentoDTO> procedimentos = service.get();
        return ResponseEntity.ok(procedimentos);
    }


    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um procedimentos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Procedimentos encontrado"),
            @ApiResponse(code = 404, message = "Procedimentos não encontrado")
    })
    public ResponseEntity<ProcedimentoDTO> get(@PathVariable("id") Long id){
        ProcedimentoDTO procedimento = service.get(id);
        if (procedimento == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(procedimento);
    }

    @PostMapping
    @ApiOperation("Cadastrar novo Procedimento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Procedimento cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar procedimento")
    })
    public ResponseEntity<?> post(@RequestBody ProcedimentoDTO dto){
        try{
            ProcedimentoDTO procedimento = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(procedimento);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um Procedimento")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProcedimentoDTO dto) {
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
    @ApiOperation("Exclui um Procedimento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Procedimento excluido com sucesso"),
            @ApiResponse(code = 404, message = "Procedimento não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Procedimento") Long id) {
        ProcedimentoDTO procedimento = service.get(id);
        if (procedimento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Procedimento não encontrada");
        }
        try {
            service.delete(procedimento);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
