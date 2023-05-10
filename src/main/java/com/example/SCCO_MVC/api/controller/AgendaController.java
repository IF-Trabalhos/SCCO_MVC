package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.*;

import com.example.SCCO_MVC.service.DentistaService;
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
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor

public class AgendaController {
    private final AgendaService service;
    private final DisponibilidadeService disponibilidadeService;
    private final DentistaService dentistaService;

    @GetMapping()
    public ResponseEntity get(){
        List<Agenda> agendas = service.getAgendas();
        return ResponseEntity.ok(agendas.stream().map(AgendaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Agenda")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Agenda encontrado"),
            @ApiResponse(code = 404, message = "Agenda não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Agenda") Long id){
        Optional<Agenda> agendas = service.getAgendaById(id);
        if (!agendas.isPresent()){
            return  new ResponseEntity("Agenda não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(agendas.map(AgendaDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar novo Agenda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Agenda cadastrado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar agenda")
    })
    public ResponseEntity post(@RequestBody AgendaDTO dto){
        try{
            Agenda agenda = converter(dto);
            agenda = service.salvar(agenda);
            return new ResponseEntity(agenda, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza Agenda")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AgendaDTO dto) {
        if (!service.getAgendaById(id).isPresent()) {
            return new ResponseEntity("Agenda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Agenda agenda = converter(dto);
            agenda.setId(id);
            service.salvar(agenda);
            return ResponseEntity.ok(agenda);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um Agenda")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Agenda excluido com sucesso"),
            @ApiResponse(code = 404, message = "Agenda não encontrado")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do Agenda") Long id) {
        Optional<Agenda> agenda = service.getAgendaById(id);
        if(!agenda.isPresent()){
            return new ResponseEntity("Agenda não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(agenda.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch(RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    public Agenda converter(AgendaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Agenda agenda = modelMapper.map(dto, Agenda.class);
        if (dto.getDisponibilidadeId() != null) {
            Optional<Disponibilidade> disponibilidade = disponibilidadeService.getDisponibilidadeById
                    (dto.getDisponibilidadeId());
            if (!disponibilidade.isPresent()) {
                agenda.setDisponibilidade(null);
            } else {
                agenda.setDisponibilidade(disponibilidade.get());
            }
        }
        if (dto.getDentistaId() != null) {
            Optional<Dentista> dentista = dentistaService.getDentistaById(dto.getDentistaId());
            if (!dentista.isPresent()) {
                agenda.setDentista(null);
            } else {
                agenda.setDentista(dentista.get());
            }
        }
        return agenda;
    }
}
