package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.DentistaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.service.DentistaService;
import com.example.SCCO_MVC.service.EnderecoService;
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
@RequestMapping("api/v1/dentistas")
@RequiredArgsConstructor
public class DentistaController {
    private final DentistaService service;
    private final EnderecoService enderecoService;

    @GetMapping
    @ApiOperation("Retorna a lista de dentistas no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de dentistas retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de dentistas")
    })
    public ResponseEntity get(){
        List<Dentista> dentistas = service.getDentistas();
        return ResponseEntity.ok(dentistas.stream().map(DentistaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Dentista")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Dentista encontrado"),
            @ApiResponse(code = 404, message = "Dentista não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Dentista") Long id){
        Optional<Dentista> dentistas = service.getDentistaById(id);
        if (!dentistas.isPresent()){
            return  new ResponseEntity("Dentista não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dentistas.map(DentistaDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar novo Dentista")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Dentista cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar o Dentista")
    })
    public ResponseEntity post(@RequestBody DentistaDTO dto){
        try{
            Dentista dentista = converter(dto);
            Endereco endereco = enderecoService.salvar(dentista.getEndereco());
            dentista.setEndereco(endereco);
            dentista = service.salvar(dentista);
            return new ResponseEntity(dentista, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Dentista")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Dentista excluido com sucesso"),
            @ApiResponse(code = 404, message = "Dentista não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Dentista") Long id) {
        Optional<Dentista> dentista = service.getDentistaById(id);
        if (!dentista.isPresent()) {
            return new ResponseEntity("Dentista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(dentista.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody DentistaDTO dto) {
        if (!service.getDentistaById(id).isPresent()) {
            return new ResponseEntity("Dentista não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Dentista dentista = converter(dto);
            dentista.setId(id);
            service.salvar(dentista);
            return ResponseEntity.ok(dentista);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Dentista converter(DentistaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Dentista dentista = modelMapper.map(dto, Dentista.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        dentista.setEndereco(endereco);
        return dentista;
    }
}
