package com.example.SCCO_MVC.api.controller;


import com.example.SCCO_MVC.api.dto.ConsultaDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.*;
import com.example.SCCO_MVC.service.ConsultaService;
import com.example.SCCO_MVC.service.DentistaService;
import com.example.SCCO_MVC.service.PacienteService;
import com.example.SCCO_MVC.service.ProcedimentoService;
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
@RequestMapping("api/v1/consultas")
@RequiredArgsConstructor
@CrossOrigin
public class ConsultaController {
    private final ConsultaService service;
    private final ProcedimentoService procedimentoService;
    private final DentistaService dentistaService;
    private final PacienteService pacienteService;

    @GetMapping
    @ApiOperation("Retorna a lista de consultas no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de consultas retornadas com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de consultas")
    })
    public ResponseEntity get(){
        List<Consulta> consultas = service.getConsultas();
        return ResponseEntity.ok(consultas.stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma consulta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta encontrada"),
            @ApiResponse(code = 404, message = "Consulta não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Consulta> consultas= service.getConsultaById(id);
        if (!consultas.isPresent()){
            return  new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(consultas.map(ConsultaDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar nova Consulta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Consulta cadastrada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar consulta")
    })
    public ResponseEntity post(@RequestBody ConsultaDTO dto){
        try{
            Consulta consulta = converter(dto);
            consulta = service.salvar(consulta);
            return new ResponseEntity(consulta, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Consulta")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ConsultaDTO dto) {
        if (!service.getConsultaById(id).isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Consulta consulta = converter(dto);
            consulta.setId(id);
            service.salvar(consulta);
            return ResponseEntity.ok(consulta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma Consulta")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Consulta excluida com sucesso"),
            @ApiResponse(code = 404, message = "Consulta não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Consulta") Long id) {
        Optional<Consulta> consulta = service.getConsultaById(id);
        if (!consulta.isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(consulta.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Consulta converter (ConsultaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Consulta consulta = modelMapper.map(dto, Consulta.class);
        if (dto.getProcedimentoId() != null) {
            Optional<Procedimento> procedimento = procedimentoService.getProcedimentoById(dto.getProcedimentoId());
            if (!procedimento.isPresent()) {
                consulta.setProcedimento(null);
            } else {
                consulta.setProcedimento(procedimento.get());
            }
        }
        if (dto.getDentistaId() != null) {
            Optional<Dentista> dentista = dentistaService.getDentistaById(dto.getDentistaId());
            if (!dentista.isPresent()) {
                consulta.setDentista(null);
            } else {
                consulta.setDentista(dentista.get());
            }
        }
        if (dto.getPacienteId() != null) {
            Optional<Paciente> paciente = pacienteService.getPacienteById(dto.getPacienteId());
            if (!paciente.isPresent()) {
                consulta.setPaciente(null);
            } else {
                consulta.setPaciente(paciente.get());
            }
        }
        return consulta;
    }
}
