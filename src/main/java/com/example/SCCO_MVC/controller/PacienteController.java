package com.example.SCCO_MVC.controller;


import com.example.SCCO_MVC.dto.PacienteDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.PacienteService;
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
@RequestMapping("api/v1/pacientes")
@RequiredArgsConstructor
@CrossOrigin
public class  PacienteController {
    private final PacienteService service;

    @GetMapping
    @ApiOperation("Retorna a lista de pacientes no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de pacientes retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de pacientes")
    })
    public ResponseEntity<List<PacienteDTO>> get(){
        List<PacienteDTO> pacientes = service.get();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/ativos")
    @ApiOperation("Retorna a lista de pacientes ativos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de pacientes retornada com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de pacientes")
    })
    public ResponseEntity<List<PacienteDTO>> getAtivos(){
        List<PacienteDTO> pacientes = service.getAtivos();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um paciente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paciente encontrado"),
            @ApiResponse(code = 404, message = "Paciente não encontrado")
    })
    public ResponseEntity<PacienteDTO> get(@PathVariable("id") Long id){
        PacienteDTO paciente = service.get(id);
        if (paciente == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(paciente);
    }

    @PostMapping
    @ApiOperation("Cadastrar novo paciente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Paciente cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar paciente")
    })
    public ResponseEntity<?> post(@RequestBody PacienteDTO dto){
        try{
            PacienteDTO paciente = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um Paciente")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody PacienteDTO dto) {
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
    @ApiOperation("Exclui um Paciente")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Paciente excluido com sucesso"),
            @ApiResponse(code = 404, message = "Paciente não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Paciente") Long id) {
        PacienteDTO paciente = service.get(id);
        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrada");
        }
        try {
            service.delete(paciente);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
