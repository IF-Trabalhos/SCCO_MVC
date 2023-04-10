package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Consulta;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.util.Date;

public class ConsultaDTO {
    private Long id;
    private Long pacienteId;
    private Long dentistaId;
    private Long tratamentoId;

    private Double valorConsulta;
    private Date data;
    private Time horaInicio;
    private Time horaFim;
    private Long agendaId;

    public static ConsultaDTO create(Consulta consulta){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consulta, ConsultaDTO.class);
    }
}
