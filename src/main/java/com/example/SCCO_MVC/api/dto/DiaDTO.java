package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Agenda;
import com.example.SCCO_MVC.model.entity.Consulta;
import com.example.SCCO_MVC.model.entity.Dia;
import com.example.SCCO_MVC.model.entity.Disponibilidade;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.util.Date;

public class DiaDTO {
    private Long id;

    private String nome;
    private Time horaInicial;
    private Time horaFinal;
    private Long disponibilidadeId;

    public static DiaDTO create(Dia dia){
        ModelMapper modelMapper = new ModelMapper();
        DiaDTO dto = modelMapper.map(dia, DiaDTO.class);
        return dto;
    }
}
