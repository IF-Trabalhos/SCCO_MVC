package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.SecretariaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.model.entity.Secretaria;
import com.example.SCCO_MVC.service.EnderecoService;
import com.example.SCCO_MVC.service.SecretariaService;
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
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Secretaria> secretarias = service.getSecretariaById(id);
        if (!secretarias.isPresent()){
            return  new ResponseEntity("Secretaria não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(secretarias.map(SecretariaDTO::create));

    }

    @PostMapping
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

    public Secretaria converter(SecretariaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Secretaria secretaria = modelMapper.map(dto, Secretaria.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        secretaria.setEndereco(endereco);
        return secretaria;
    }
}
