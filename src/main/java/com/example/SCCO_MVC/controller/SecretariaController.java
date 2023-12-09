package com.example.SCCO_MVC.controller;


import com.example.SCCO_MVC.dto.SecretariaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.SecretariaService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secretarias")
@RequiredArgsConstructor
@Api("Api de Secretario(a)")
@CrossOrigin
public class SecretariaController {
    private final SecretariaService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de secretarios(as) no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de secretarios(as) retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de secretarios(as)")
    })
    public ResponseEntity<List<SecretariaDTO>> get(){
        List<SecretariaDTO> secretarias = service.get();
        return ResponseEntity.ok(secretarias);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Obter detalhes de um(a) secretario(a)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Secretario(a) encontrado"),
            @ApiResponse(code = 404, message = "Secretario(a) não encontrado")
    })
    public ResponseEntity<SecretariaDTO> get(@PathVariable("id") Long id){
        SecretariaDTO secretaria = service.get(id);
        if (secretaria == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(secretaria);
    }


    @PostMapping
    @ApiOperation(value = "Cadastrar novo(a) secretario(a)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Secretario(a) salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar secretario(a)")
    })
    public ResponseEntity<?> post(@RequestBody SecretariaDTO dto){
        try{
            SecretariaDTO secretaria = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(secretaria);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza secretaria")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SecretariaDTO dto) {
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
    @ApiOperation(value = "Exclui um(a) secretario(a)")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Secretario(a) excluido com sucesso"),
            @ApiResponse(code = 404, message = "Secretario(a) não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Secretaria") Long id) {
        SecretariaDTO secretaria = service.get(id);
        if (secretaria == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Secretaria não encontrada");
        }
        try {
            service.delete(secretaria);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
