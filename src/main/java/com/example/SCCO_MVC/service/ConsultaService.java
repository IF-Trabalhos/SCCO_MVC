package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.dto.ConsultaDTO;
import com.example.SCCO_MVC.domain.Consulta;
import com.example.SCCO_MVC.model.entity.ConsultaEntity;
import com.example.SCCO_MVC.model.repository.ConsultaRepository;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultaService {

    private final ConsultaRepository repository;

    public List<ConsultaDTO> get(){
        List<ConsultaEntity> consultas = this.repository.findAll();
        List<ConsultaDTO> consultasDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( ConsultaEntity consulta: consultas ) {
            consultasDTO.add(ConsultaDTO.fromEntityToDTO(consulta));
        }

        return consultasDTO;
    }

    public ConsultaDTO get(Long id){
        Optional<ConsultaEntity> consulta = this.repository.findById(id);
        return consulta.map(ConsultaDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public ConsultaDTO save(ConsultaDTO dto){
        Consulta consulta = dto.fromDTOToDomain();
        consulta.validate();
        ConsultaEntity entity = this.repository.save(dto.fromDTOToEntity());
        return ConsultaDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(ConsultaDTO dto){
        ConsultaEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
