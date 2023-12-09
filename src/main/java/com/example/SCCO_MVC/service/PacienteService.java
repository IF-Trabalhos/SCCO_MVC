package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Paciente;
import com.example.SCCO_MVC.dto.PacienteDTO;
import com.example.SCCO_MVC.model.entity.PacienteEntity;
import com.example.SCCO_MVC.model.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PacienteService {
    public PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<PacienteDTO> get(){
        List<PacienteEntity> pacientes = this.repository.findAll();
        List<PacienteDTO> pacientesDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( PacienteEntity paciente: pacientes ) {
            pacientesDTO.add(PacienteDTO.fromEntityToDTO(paciente));
        }
        return pacientesDTO;
    }

    public List<PacienteDTO> getAtivos(){
        List<PacienteEntity> pacientes = this.repository.findAllByAtivoTrue();
        List<PacienteDTO> pacientesDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( PacienteEntity paciente: pacientes ) {
            pacientesDTO.add(PacienteDTO.fromEntityToDTO(paciente));
        }
        return pacientesDTO;
    }

    public PacienteDTO get(Long id){
        Optional<PacienteEntity> paciente = this.repository.findById(id);
        return paciente.map(PacienteDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public PacienteDTO save(PacienteDTO dto){
        Paciente paciente = dto.fromDTOToDomain();
        paciente.validate();
        PacienteEntity entity = this.repository.save(dto.fromDTOToEntity());
        return PacienteDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(PacienteDTO dto){
        PacienteEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}