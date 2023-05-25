package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.ContaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Consulta;
import com.example.SCCO_MVC.model.entity.Conta;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.service.ConsultaService;
import com.example.SCCO_MVC.service.ContaService;
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
@RequestMapping("api/v1/contas")
@RequiredArgsConstructor
@CrossOrigin
public class ContaController {
    private final ContaService service;

    private final ConsultaService consultaService;

    private final PacienteService pacienteService;

    @GetMapping()
    @ApiOperation("Retorna a lista de Conta no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de Conta retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de Conta")
    })
    public ResponseEntity get(){
        List<Conta> contas = service.getContas();
        return ResponseEntity.ok(contas.stream().map(ContaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Conta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Conta encontrado"),
            @ApiResponse(code = 404, message = "Conta não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Paciente") Long id){
        Optional<Conta> contas = service.getContaById(id);
        if (!contas.isPresent()){
            return  new ResponseEntity("Conta não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(contas.map(ContaDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar nova Conta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Conta cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar Conta")
    })
    public ResponseEntity post(@RequestBody ContaDTO dto){
        try{
            Conta conta = converter(dto);
            conta = service.salvar(conta);
            return new ResponseEntity(conta, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Conta")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ContaDTO dto) {
        if (!service.getContaById(id).isPresent()) {
            return new ResponseEntity("Conta não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Conta conta = converter(dto);
            conta.setId(id);
            service.salvar(conta);
            return ResponseEntity.ok(conta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma Conta")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Conta excluido com sucesso"),
            @ApiResponse(code = 404, message = "Conta não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Conta") Long id) {
        Optional<Conta> conta = service.getContaById(id);
        if (!conta.isPresent()) {
            return new ResponseEntity("Conta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(conta.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Conta converter(ContaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Conta conta = modelMapper.map(dto, Conta.class);
        if (dto.getPacienteId() != null) {
            Optional<Paciente> paciente = pacienteService.getPacienteById(dto.getPacienteId());
            if (!paciente.isPresent()) {
                conta.setPaciente(null);
            } else {
                conta.setPaciente(paciente.get());
            }
        }
        if (dto.getConsultaId() != null) {
            Optional<Consulta> consulta = consultaService.getConsultaById(dto.getConsultaId());
            if (!consulta.isPresent()) {
                conta.setConsulta(null);
            } else {
                conta.setConsulta(consulta.get());
            }
        }
        return conta;
    }
}