package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Dia;
import org.modelmapper.ModelMapper;

import java.sql.Time;

public class DiaDTO {
    private Long id;

    private String nome;
    private Time horaInicial;
    private Time horaFinal;
    private Long disponibilidadeId;

    public static DiaDTO create(Dia dia){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dia, DiaDTO.class);
    }
}
