package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Agenda;
import com.example.SCCO_MVC.model.entity.Consulta;
import com.example.SCCO_MVC.model.entity.Disponibilidade;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.util.Date;

public class DisponibilidadeDTO {
    private Long id;
    private Time horaInicialIntervalo;
    private Time horaFinalIntervalo;

    public static DisponibilidadeDTO create(Disponibilidade disponibilidade){
        ModelMapper modelMapper = new ModelMapper();
        DisponibilidadeDTO dto = modelMapper.map(disponibilidade, DisponibilidadeDTO.class);
        return dto;
    }
}

