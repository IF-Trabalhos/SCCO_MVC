package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.EspecialidadeDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Especialidade;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.service.EspecialidadeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {
    private final EspecialidadeService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Especialidade> especialidades = service.getEspecialidades();
        return ResponseEntity.ok(especialidades.stream().map(EspecialidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Especialidade> especialidades = service.getEspecialidadeById(id);
        if (!especialidades.isPresent()){
            return  new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(especialidades.map(EspecialidadeDTO::create));
    }
    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Especialidade> especialidade = service.getEspecialidadeById(id);
        if (!especialidade.isPresent()) {
            return new ResponseEntity("Especialidade não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(especialidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity post(@RequestBody EspecialidadeDTO dto){
        try{
            Especialidade especialidade = converter(dto);
            especialidade = service.salvar(especialidade);
            return new ResponseEntity(especialidade, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EspecialidadeDTO dto) {
        if (!service.getEspecialidadeById(id).isPresent()) {
            return new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Especialidade especialidade = converter(dto);
            especialidade.setId(id);
            service.salvar(especialidade);
            return ResponseEntity.ok(especialidade);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Especialidade converter(EspecialidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Especialidade especialidade = modelMapper.map(dto, Especialidade.class);
        return especialidade;
    }
}
