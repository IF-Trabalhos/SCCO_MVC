package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.PacienteDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.service.EnderecoService;
import com.example.SCCO_MVC.service.PacienteService;
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
@RequestMapping("api/v1/pacientes")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService service;
    private final EnderecoService enderecoService;

    @GetMapping
    @ApiOperation("Retorna a lista de pacientes no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de pacientes retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de pacientes")
    })
    public ResponseEntity get(){
        List<Paciente> pacientes = service.getPacientes();
        return ResponseEntity.ok(pacientes.stream().map(PacienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um paciente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paciente encontrado"),
            @ApiResponse(code = 404, message = "Paciente não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Paciente") Long id){
        Optional<Paciente> pacientes = service.getPacienteById(id);
        if (!pacientes.isPresent()){
            return  new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pacientes.map(PacienteDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar novo paciente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Paciente cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar paciente")
    })
    public ResponseEntity post(@RequestBody PacienteDTO dto){
        try{
            Paciente paciente = converter(dto);
            Endereco endereco = enderecoService.salvar(paciente.getEndereco());
            paciente.setEndereco(endereco);
            paciente = service.salvar(paciente);
            return new ResponseEntity(paciente, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um Paciente")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PacienteDTO dto) {
        if (!service.getPacienteById(id).isPresent()) {
            return new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Paciente paciente = converter(dto);
            paciente.setId(id);
            service.salvar(paciente);
            return ResponseEntity.ok(paciente);
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
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Paciente") Long id) {
        Optional<Paciente> paciente = service.getPacienteById(id);
        if (!paciente.isPresent()) {
            return new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(paciente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Paciente converter(PacienteDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Paciente paciente = modelMapper.map(dto, Paciente.class);
        Endereco endereco = modelMapper.map(dto, Endereco.class);
        paciente.setEndereco(endereco);
        return paciente;
    }
}
