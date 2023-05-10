package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.service.DiaService;

import com.example.SCCO_MVC.service.DisponibilidadeService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/dias")
@RequiredArgsConstructor
public class DiaController {
    private final DiaService service;
    private final DisponibilidadeService disponibilidadeService;
    @GetMapping()
    @ApiOperation("Retorna a lista de dias no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de dias retornada com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de dias")
    })
    public ResponseEntity get(){
        List<Dia> dias = service.getDias();
        return ResponseEntity.ok(dias.stream().map(DiaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um dia")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dia encontrado"),
            @ApiResponse(code = 404, message = "Dia não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Dia") Long id){
        Optional<Dia> dias = service.getDiaById(id);
        if (!dias.isPresent()){
            return  new ResponseEntity("Dia não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dias.map(DiaDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar novo dia")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Dia cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar dia")
    })
    public ResponseEntity post(@RequestBody DiaDTO dto){
        try{
            Dia dia = converter(dto);
            dia = service.salvar(dia);
            return new ResponseEntity(dia, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um Dia")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DiaDTO dto) {
        if (!service.getDiaById(id).isPresent()) {
            return new ResponseEntity("Dia não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Dia dia = converter(dto);
            dia.setId(id);
            service.salvar(dia);
            return ResponseEntity.ok(dia);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Dia")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Dia excluido com sucesso"),
            @ApiResponse(code = 404, message = "Dia não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Dia") Long id) {
        Optional<Dia> dia = service.getDiaById(id);
        if (!dia.isPresent()) {
            return new ResponseEntity("Dia não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(dia.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Dia converter(DiaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Dia dia = modelMapper.map(dto, Dia.class);
        if (dto.getDisponibilidadeId() != null) {
            Optional<Disponibilidade> disponibilidade = disponibilidadeService.getDisponibilidadeById
                    (dto.getDisponibilidadeId());
            if (!disponibilidade.isPresent()) {
                dia.setDisponibilidade(null);
            } else {
                dia.setDisponibilidade(disponibilidade.get());
            }
        }
        return dia;
    }
}
