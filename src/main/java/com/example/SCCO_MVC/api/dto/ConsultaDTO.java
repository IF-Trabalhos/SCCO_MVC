package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Consulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {
    private Long id;
    private Long pacienteId;
    private Long dentistaId;
    private Long tratamento;

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
