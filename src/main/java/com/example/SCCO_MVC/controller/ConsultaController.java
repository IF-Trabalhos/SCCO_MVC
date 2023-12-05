package com.example.SCCO_MVC.controller;


import com.example.SCCO_MVC.dto.ConsultaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.ConsultaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("api/v1/consultas")
@RequiredArgsConstructor
@CrossOrigin
public class ConsultaController {
    private final ConsultaService service;

    @GetMapping
    @ApiOperation("Retorna a lista de consultas no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de consultas retornadas com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de consultas")
    })
    public ResponseEntity<List<ConsultaDTO>> get(){
        List<ConsultaDTO> consultas = service.get();
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma consulta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta encontrada"),
            @ApiResponse(code = 404, message = "Consulta não encontrada")
    })
    public ResponseEntity<ConsultaDTO> get(@PathVariable("id") Long id){
        ConsultaDTO consulta = service.get(id);
        if (consulta == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consulta);
    }

    @PostMapping
    @ApiOperation("Cadastrar nova Consulta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Consulta cadastrada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar consulta")
    })
    public ResponseEntity<?> post(@RequestBody ConsultaDTO dto){
        try{
            ConsultaDTO consulta = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(consulta);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Consulta")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ConsultaDTO dto) {
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
    @ApiOperation("Exclui uma Consulta")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Consulta excluida com sucesso"),
            @ApiResponse(code = 404, message = "Consulta não encontrada")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Consulta") Long id) {
        ConsultaDTO consulta = service.get(id);
        if (consulta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consulta não encontrada");
        }
        try {
            service.delete(consulta);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
