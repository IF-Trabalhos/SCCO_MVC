package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Agenda;
import com.example.SCCO_MVC.domain.Conta;
import com.example.SCCO_MVC.model.entity.AgendaEntity;
import com.example.SCCO_MVC.model.entity.ContaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {
    private Long id;
    private LocalDate data;
    private Time horaInicial;
    private Time horaFinal;

    public static AgendaDTO fromEntityToDTO(AgendaEntity agendaEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agendaEntity, AgendaDTO.class);
    }

    public Agenda fromDTOToDomain(){
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(this, Agenda.class);
    }

    public AgendaEntity fromDTOToEntity(){
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(this, AgendaEntity.class);
    }
}
