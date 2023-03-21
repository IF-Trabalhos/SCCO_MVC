package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Consulta;
import com.example.SCCO_MVC.model.entity.Paciente;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.util.Date;

public class ConsultaDTO {
    private Long id;
    private Long pacienteId;
    private Long dentistaId;
    private Long tratamento;

    private double valorConsulta;
    private Date data;
    private Time horaInicio;
    private Time horaFim;

    public static ConsultaDTO create(Consulta consulta){
        ModelMapper modelMapper = new ModelMapper();
        ConsultaDTO dto = modelMapper.map(consulta, ConsultaDTO.class);
        return dto;
    }
}
