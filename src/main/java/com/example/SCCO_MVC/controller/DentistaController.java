package com.example.SCCO_MVC.controller;


import com.example.SCCO_MVC.dto.DentistaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.DentistaService;
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
@RequestMapping("api/v1/dentistas")
@RequiredArgsConstructor
@CrossOrigin
public class DentistaController {
    private final DentistaService service;

    @GetMapping
    @ApiOperation("Retorna a lista de dentistas no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de dentistas retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de dentistas")
    })
    public ResponseEntity<List<DentistaDTO>> get(){
        List<DentistaDTO> dentistas = service.get();
        return ResponseEntity.ok(dentistas);
    }
    @GetMapping("/ativos")
    @ApiOperation("Retorna a lista de pacientes ativos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de dentistas retornada com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de dentistas")
    })
    public ResponseEntity<List<DentistaDTO>> getAtivos(){
        List<DentistaDTO> dentistas = service.getAtivos();
        return ResponseEntity.ok(dentistas);
    }


    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Dentista")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dentista encontrado"),
            @ApiResponse(code = 404, message = "Dentista não encontrado")
    })
    public ResponseEntity<DentistaDTO> get(@PathVariable("id") Long id){
        DentistaDTO dentista = service.get(id);
        if (dentista == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dentista);
    }


    @PostMapping
    @ApiOperation("Cadastrar novo Dentista")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Dentista cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar o Dentista")
    })
    public ResponseEntity<?> post(@RequestBody DentistaDTO dto){
        try{
            DentistaDTO dentista = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(dentista);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza Dentista")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody DentistaDTO dto) {
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
    @ApiOperation("Exclui um Dentista")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Dentista excluido com sucesso"),
            @ApiResponse(code = 404, message = "Dentista não encontrado")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Dentista") Long id) {
        DentistaDTO dentista = service.get(id);
        if (dentista == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dentista não encontrada");
        }
        try {
            service.delete(dentista);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
