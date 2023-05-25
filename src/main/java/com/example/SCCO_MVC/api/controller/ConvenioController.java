package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ConvenioDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Convenio;
import com.example.SCCO_MVC.service.ConvenioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/convenios")
@RequiredArgsConstructor
@CrossOrigin
public class ConvenioController {
    private final ConvenioService service;

    @GetMapping
    @ApiOperation("Retorna a lista de convenios no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de convenios retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de convenios")
    })
    public ResponseEntity get(){
        List<Convenio> convenios = service.getConvenios();
        return ResponseEntity.ok(convenios.stream().map(ConvenioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Convenio")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Convenio encontrado"),
            @ApiResponse(code = 404, message = "Convenio não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Convenio") Long id){
        Optional<Convenio> convenios = service.getConvenioById(id);
        if (!convenios.isPresent()){
            return  new ResponseEntity("Convenio não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(convenios.map(ConvenioDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar novo Convenio")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Convenio cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar o Convenio")
    })
    public ResponseEntity post(@RequestBody ConvenioDTO dto){
        try{
            Convenio convenio = converter(dto);
            convenio = service.salvar(convenio);
            return new ResponseEntity(convenio, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza Convenio")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ConvenioDTO dto) {
        if (!service.getConvenioById(id).isPresent()) {
            return new ResponseEntity("Convenio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Convenio convenio = converter(dto);
            convenio.setId(id);
            service.salvar(convenio);
            return ResponseEntity.ok(convenio);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Convenio")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Convenio excluido com sucesso"),
            @ApiResponse(code = 404, message = "Convenio não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Convenio") Long id) {
        Optional<Convenio> convenio = service.getConvenioById(id);
        if (!convenio.isPresent()) {
            return new ResponseEntity("Convenio não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(convenio.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Convenio converter(ConvenioDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Convenio convenio = modelMapper.map(dto, Convenio.class);
        return convenio;
    }
}