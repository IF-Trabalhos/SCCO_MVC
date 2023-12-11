package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Paciente;
import com.example.SCCO_MVC.dto.ConvenioDTO;
import com.example.SCCO_MVC.dto.PacienteDTO;
import com.example.SCCO_MVC.model.entity.ConvenioEntity;
import com.example.SCCO_MVC.model.entity.PacienteEntity;
import com.example.SCCO_MVC.model.repository.ConvenioRepository;
import com.example.SCCO_MVC.model.repository.PacienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@AllArgsConstructor
public class PacienteService {
    public PacienteRepository pacienteRepository;

    public ConvenioRepository convenioRepository;

    public List<PacienteDTO> get(){
        List<PacienteEntity> pacientes = this.pacienteRepository.findAll();
        List<PacienteDTO> pacientesDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( PacienteEntity paciente: pacientes ) {
            pacientesDTO.add(PacienteDTO.fromEntityToDTO(paciente));
        }
        return pacientesDTO;
    }

    public List<PacienteDTO> getAtivos(){
        List<PacienteEntity> pacientes = this.pacienteRepository.findAllByAtivoTrue();
        List<PacienteDTO> pacientesDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( PacienteEntity paciente: pacientes ) {
            pacientesDTO.add(PacienteDTO.fromEntityToDTO(paciente));
        }
        return pacientesDTO;
    }

    public PacienteDTO get(Long id){
        Optional<PacienteEntity> paciente = this.pacienteRepository.findById(id);
        return paciente.map(PacienteDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public PacienteDTO save(PacienteDTO dto){
        ConvenioEntity convenioEntity = this.convenioRepository.getById(dto.getConvenioId());
        Paciente paciente = dto.fromDTOToDomain();
        ConvenioDTO convenioDTO = ConvenioDTO.fromEntityToDTO(convenioEntity);
        paciente.setConvenio(convenioDTO.fromDTOToDomain());
        paciente.validate();
        PacienteEntity pacienteEntity = dto.fromDTOToEntity();
        pacienteEntity.setConvenioEntity(convenioEntity);
        PacienteEntity entity = this.pacienteRepository.save(pacienteEntity);
        return PacienteDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(PacienteDTO dto){
        PacienteEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.pacienteRepository.delete(entity);
    }
}