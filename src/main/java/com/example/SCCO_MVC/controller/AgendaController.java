package com.example.SCCO_MVC.controller;

import com.example.SCCO_MVC.dto.AgendaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.AgendaService;
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
@RequestMapping("api/v1/agendas")
@RequiredArgsConstructor
@CrossOrigin
public class AgendaController {
    private final AgendaService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de agendas no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de Agenda retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de Agenda")
    })
    public ResponseEntity<List<AgendaDTO>> get(){
        List<AgendaDTO> agendas = service.get();
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma agenda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Agenda encontrada"),
            @ApiResponse(code = 404, message = "Agenda não encontrada")
    })
    public ResponseEntity<AgendaDTO> get(@PathVariable("id") Long id){
        AgendaDTO agenda = service.get(id);
        if (agenda == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(agenda);
    }

    @PostMapping
    @ApiOperation("Cadastrar nova agenda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Agenda cadastrada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar agenda")
    })
    public ResponseEntity<?> post(@RequestBody AgendaDTO dto){
        try{
            AgendaDTO agenda = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(agenda);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma agenda")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody AgendaDTO dto) {
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
    @ApiOperation("Exclui uma agenda")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Agenda excluido com sucesso"),
            @ApiResponse(code = 404, message = "Agenda não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id da agenda") Long id) {
        AgendaDTO agenda = service.get(id);
        if (agenda == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agenda não encontrada");
        }
        try {
            service.delete(agenda);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}