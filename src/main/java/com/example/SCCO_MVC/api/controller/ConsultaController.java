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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.*;
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

    @GetMapping("/{dataInicial}/{dataFinal}")
    @ApiOperation("Obter detalhes de uma consulta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta encontrada"),
            @ApiResponse(code = 404, message = "Consulta não encontrada")
    })
    public ResponseEntity get(@PathVariable("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                              @PathVariable("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal){
        List<Consulta> consultas = service.getConsultaByDatas(dataInicial, dataFinal);
        return ResponseEntity.ok(consultas.stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/quantidade/{dataInicial}/{dataFinal}")
    @ApiOperation("Retorna a quantidade de consultas em um intervalo de tempo no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Quantidade de consultas retornadas com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a quantiade de consultas")
    })
    public ResponseEntity<Integer> getQuantidadeConsultasByData(@PathVariable("dataInicial")
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                LocalDate dataInicial,
                                                                @PathVariable("dataFinal")
                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                LocalDate dataFinal) {
        Integer qtdConsultas = service.getConsultaByDatas(dataInicial, dataFinal).size();
        return ResponseEntity.ok(qtdConsultas);
    }

    @GetMapping("/paciente/quantidade/{dataInicial}/{dataFinal}")
    @ApiOperation("Retorna a quantidade de pacientes consultados em um intervalo de tempo no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Quantidade de pacientes consultados retornadas com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar a quantiade de pacientes consultados")
    })
    public ResponseEntity<Integer> getQuantidadePacientesByData(@PathVariable("dataInicial")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                          LocalDate dataInicial,
                                                          @PathVariable("dataFinal")
                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                          LocalDate dataFinal) {
        Set<Paciente> pacientesUnicos = new HashSet<>();

        for (Consulta consulta : service.getConsultaByDatas(dataInicial, dataFinal)){
            pacientesUnicos.add(consulta.getPaciente());
        }

        int totalPacientes = pacientesUnicos.size();

        return ResponseEntity.ok(totalPacientes);
    }

    @GetMapping("/valor/{dataInicial}/{dataFinal}")
    @ApiOperation("Retorna o valor total das consultas em um intervalo de tempo no sistema")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valor total retornado com sucesso"),
            @ApiResponse(code = 500, message = "Ocorreu um erro ao buscar o valor total")
    })
    public ResponseEntity<Float> getValorTotalByData(@PathVariable("dataInicial")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
                                               @PathVariable("dataFinal")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {

        float valorTotal = 0;

        for (Consulta consulta : service.getConsultaByDatas(dataInicial, dataFinal)){
            if (consulta.getValorConsulta() != null){
                valorTotal += consulta.getValorConsulta();
            }
        }

        return ResponseEntity.ok(valorTotal);
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
    @GetMapping("/dentista/{id}")
    public ResponseEntity getConsultasByDentistaId(@PathVariable("id") Long id){
        Optional<Consulta> consultas = service.getConsultaByDentistaId(id);
        if (!consultas.isPresent()){
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(consultas.map(ConsultaDTO::create));
    }
    public  ResponseEntity getConsultasByData(@PathVariable("Data") Date data){
        Optional<Consulta> consultas = service.getConsultaByData(data);
        if(!consultas.isPresent()){
            return new ResponseEntity("Consulta não encontrada",HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.ok(consultas.map(ConsultaDTO::create));
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
