package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Expediente;
import com.example.SCCO_MVC.dto.ExpedienteDTO;
import com.example.SCCO_MVC.model.entity.ExpedienteEntity;
import com.example.SCCO_MVC.model.repository.ExpedienteRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpedienteService {

    private final ExpedienteRepository repository;

    public ExpedienteService(ExpedienteRepository repository){
        this.repository = repository;
    }

    public List<ExpedienteDTO> get(){
        List<ExpedienteEntity> expedientes = this.repository.findAll();
        List<ExpedienteDTO> expedientesDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( ExpedienteEntity expediente: expedientes ) {
            expedientesDTO.add(ExpedienteDTO.fromEntityToDTO(expediente));
        }

        return expedientesDTO;
    }

    public ExpedienteDTO get(Long id){
        Optional<ExpedienteEntity> expediente = this.repository.findById(id);
        return expediente.map(ExpedienteDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public ExpedienteDTO save(ExpedienteDTO dto){
        Expediente expediente = dto.fromDTOToDomain();
        expediente.validate();
        ExpedienteEntity entity = this.repository.save(dto.fromDTOToEntity());
        return ExpedienteDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(ExpedienteDTO dto){
        ExpedienteEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
