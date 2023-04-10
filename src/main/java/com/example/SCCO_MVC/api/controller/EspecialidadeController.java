package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.EspecialidadeDTO;
import com.example.SCCO_MVC.model.entity.Especialidade;
import com.example.SCCO_MVC.service.EspecialidadeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/especialidades")
@RequiredArgsConstructor
public class EspecialidadeController {
    private EspecialidadeService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Especialidade> especialidades = service.getEspecialidades();
        return ResponseEntity.ok(especialidades.stream().map(EspecialidadeDTO::create).collect(Collectors.toList()));
    }
}
