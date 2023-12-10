package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Agenda;
import com.example.SCCO_MVC.dto.AgendaDTO;
import com.example.SCCO_MVC.model.entity.AgendaEntity;
import com.example.SCCO_MVC.model.repository.AgendaRepository;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AgendaService {

    private final AgendaRepository repository;

    public List<AgendaDTO> get(){
        List<AgendaEntity> agendas = this.repository.findAll();
        List<AgendaDTO> agendasDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( AgendaEntity agenda: agendas ) {
            agendasDTO.add(AgendaDTO.fromEntityToDTO(agenda));
        }

        return agendasDTO;
    }

    public AgendaDTO get(Long id){
        Optional<AgendaEntity> agenda = this.repository.findById(id);
        return agenda.map(AgendaDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public AgendaDTO save(AgendaDTO dto){
        Agenda agenda = dto.fromDTOToDomain();
        agenda.validate();
        AgendaEntity entity = this.repository.save(dto.fromDTOToEntity());
        return AgendaDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(AgendaDTO dto){
        AgendaEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
