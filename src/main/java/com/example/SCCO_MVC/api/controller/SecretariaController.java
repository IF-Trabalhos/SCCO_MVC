package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.SecretariaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.model.entity.Secretaria;
import com.example.SCCO_MVC.service.EnderecoService;
import com.example.SCCO_MVC.service.SecretariaService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/v1/secretarias")
@RequiredArgsConstructor
public class SecretariaController {
    private final SecretariaService service;
    private final EnderecoService enderecoService;

    @GetMapping()
    public ResponseEntity get(){
        List<Secretaria> secretarias = service.getSecretarias();
        return ResponseEntity.ok(secretarias.stream().map(SecretariaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um(a) secretario(a)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Secretario(a) encontrado"),
            @ApiResponse(code = 404, message = "Secretario(a) não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Secretaria> secretarias = service.getSecretariaById(id);
        if (!secretarias.isPresent()){
            return  new ResponseEntity("Secretaria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(secretarias.map(SecretariaDTO::create));
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Secretaria> secretaria = service.getSecretariaById(id);
        if (!secretaria.isPresent()) {
            return new ResponseEntity("Secretaria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(secretaria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping
    @ApiOperation("Cadastrar novo(a) secretario(a)")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Secretario(a) salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar secretario(a)")
    })
    public ResponseEntity post(@RequestBody SecretariaDTO dto){
        try{
            Secretaria secretaria = converter(dto);
            Endereco endereco = enderecoService.salvar(secretaria.getEndereco());
            secretaria.setEndereco(endereco);
            secretaria = service.salvar(secretaria);
            return new ResponseEntity(secretaria, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SecretariaDTO dto) {
        if (!service.getSecretariaById(id).isPresent()) {
            return new ResponseEntity("Secretaria não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Secretaria secretaria = converter(dto);
            secretaria.setId(id);
            service.salvar(secretaria);
            return ResponseEntity.ok(secretaria);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Secretaria converter(SecretariaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Secretaria secretaria = modelMapper.map(dto, Secretaria.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        secretaria.setEndereco(endereco);
        return secretaria;
    }
}
