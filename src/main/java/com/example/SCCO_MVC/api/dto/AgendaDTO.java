package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Agenda;
import com.example.SCCO_MVC.model.entity.Consulta;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.util.Date;

public class AgendaDTO {
    private Long id;
    private Double faturamento;
    private Long disponibilidadeId;

    public static AgendaDTO create(Agenda agenda){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agenda, AgendaDTO.class);
    }
}
