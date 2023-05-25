package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.EspecialidadeDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Especialidade;
import com.example.SCCO_MVC.service.EspecialidadeService;
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
@RequestMapping("api/v1/especialidades")
@RequiredArgsConstructor
@CrossOrigin
public class EspecialidadeController {
    private final EspecialidadeService service;

    @GetMapping()
    @ApiOperation("Retorna a lista de Especialidade no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lista de especialidade retornada com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a lista de especialidade")
    })
    public ResponseEntity get(){
        List<Especialidade> especialidades = service.getEspecialidades();
        return ResponseEntity.ok(especialidades.stream().map(EspecialidadeDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Especialidade")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Especialidade encontrada"),
            @ApiResponse(code = 404, message = "Especialidade não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do Paciente") Long id){
        Optional<Especialidade> especialidades = service.getEspecialidadeById(id);
        if (!especialidades.isPresent()){
            return  new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(especialidades.map(EspecialidadeDTO::create));
    }

    @PostMapping
    @ApiOperation("Cadastrar nova especialidade")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Especialidade cadastrada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao cadastrar especialidade")
    })
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
    @ApiOperation("Atualiza uma Especialidade")
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
    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma Especialidade")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Especialidade excluida com sucesso"),
            @ApiResponse(code = 404, message = "Especialidade não encontrada")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id da Especialidade") Long id) {
        Optional<Especialidade> especialidade = service.getEspecialidadeById(id);
        if (!especialidade.isPresent()) {
            return new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(especialidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    public Especialidade converter(EspecialidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Especialidade especialidade = modelMapper.map(dto, Especialidade.class);
        return especialidade;
    }
}
