package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Consulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {
    private Long id;
    private Double valorConsulta;
    private LocalDate data;
    private Time horaInicial;
    private Time horaFinal;
    private Long pacienteId;
    private Long dentistaId;
    private Long procedimentoId;

    public static ConsultaDTO create(Consulta consulta){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(consulta, ConsultaDTO.class);
    }
}
