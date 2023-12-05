package com.example.SCCO_MVC.controller;

import com.example.SCCO_MVC.dto.EspecialidadeDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.EspecialidadeService;
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
@RequestMapping("api/v1/especialidades")
@RequiredArgsConstructor
@CrossOrigin
public class EspecialidadeController {
    private final EspecialidadeService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de Especialidade no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de especialidade retornada com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de especialidade")
    })
    public ResponseEntity<List<EspecialidadeDTO>> get(){
        List<EspecialidadeDTO> especialidades = service.get();
        return ResponseEntity.ok(especialidades);
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Especialidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Especialidade encontrada"),
            @ApiResponse(code = 404, message = "Especialidade não encontrada")
    })
    public ResponseEntity<EspecialidadeDTO> get(@PathVariable("id") Long id){
        EspecialidadeDTO especialidade = service.get(id);
        if (especialidade == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(especialidade);
    }

    @PostMapping
    @ApiOperation("Cadastrar nova especialidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Especialidade cadastrada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar especialidade")
    })
    public ResponseEntity<?> post(@RequestBody EspecialidadeDTO dto){
        try{
            EspecialidadeDTO especialidade = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(especialidade);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Especialidade")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody EspecialidadeDTO dto) {
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
    @ApiOperation("Exclui uma Especialidade")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Especialidade excluida com sucesso"),
            @ApiResponse(code = 404, message = "Especialidade não encontrada")
    })
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do Especialidade") Long id) {
        EspecialidadeDTO especialidade = service.get(id);
        if (especialidade == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Especialidade não encontrada");
        }
        try {
            service.delete(especialidade);
            return ResponseEntity.noContent().build();
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
