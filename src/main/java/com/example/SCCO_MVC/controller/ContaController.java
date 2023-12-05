package com.example.SCCO_MVC.controller;

import com.example.SCCO_MVC.dto.ContaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.ContaService;
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
@RequestMapping("api/v1/contas")
@RequiredArgsConstructor
@CrossOrigin
public class ContaController {
    private final ContaService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de Conta no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de Conta retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de Conta")
    })
    public ResponseEntity<List<ContaDTO>> get(){
        List<ContaDTO> contas = service.get();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Conta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Conta encontrado"),
            @ApiResponse(code = 404, message = "Conta não encontrado")
    })
    public ResponseEntity<ContaDTO> get(@PathVariable("id") Long id){
        ContaDTO conta = service.get(id);
        if (conta == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(conta);
    }

    @PostMapping
    @ApiOperation("Cadastrar nova Conta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Conta cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar Conta")
    })
    public ResponseEntity<?> post(@RequestBody ContaDTO dto){
        try{
            ContaDTO conta = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(conta);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Conta")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ContaDTO dto) {
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
    @ApiOperation("Exclui uma Conta")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Conta excluido com sucesso"),
            @ApiResponse(code = 404, message = "Conta não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Conta") Long id) {
        ContaDTO conta = service.get(id);
        if (conta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
        }
        try {
            service.delete(conta);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}