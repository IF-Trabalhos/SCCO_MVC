package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.exception.RegraNegocioException;
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
@RequestMapping("/api/v1/disponibilidades")
@RequiredArgsConstructor
@Api("Api de Disponibilidade")
public class DisponibilidadeController {
    private final DisponibilidadeService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de disponibilidades no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de disponibilidades retornada com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de disponibilidades")
    })
    public ResponseEntity get(){
        List<Disponibilidade> disponibilidades= service.getDisponibilidades();
        return ResponseEntity.ok(disponibilidades.stream().map(DisponibilidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um disponibilidades")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Disponibilidade encontrada"),
            @ApiResponse(code = 404, message = "Disponibilidade não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Disponibilidade> disponibilidade = service.getDisponibilidadeById(id);
        if (!disponibilidade.isPresent()){
            return  new ResponseEntity("Disponibilidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(disponibilidade.map(DisponibilidadeDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar nova disponibilidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Disponibilidade cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar disponibilidade")
    })
    public ResponseEntity post(@RequestBody DisponibilidadeDTO dto){
        try{
            Disponibilidade disponibilidade = converter(dto);
            disponibilidade = service.salvar(disponibilidade);
            return new ResponseEntity(disponibilidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Disponibilidade")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DisponibilidadeDTO dto) {
        if (!service.getDisponibilidadeById(id).isPresent()) {
            return new ResponseEntity("Disponibilidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Disponibilidade disponibilidade = converter(dto);
            disponibilidade.setId(id);
            service.salvar(disponibilidade);
            return ResponseEntity.ok(disponibilidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Disponibilidade")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Disponibilidade excluida com sucesso"),
            @ApiResponse(code = 404, message = "Disponibilidade não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id da disponibilidade") Long id) {



        Optional<Disponibilidade> disponibilidade = service.getDisponibilidadeById(id);
        if (!disponibilidade.isPresent()) {
            return new ResponseEntity("Disponibilidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(disponibilidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Disponibilidade converter(DisponibilidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Disponibilidade disponibilidade = modelMapper.map(dto, Disponibilidade.class);
        return disponibilidade;
    }
}
