package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Expediente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteDTO {
    private Long id;

    private String diaDaSemana;
    private Time horaInicial;
    private Time horaFinal;

    public static ExpedienteDTO create(Expediente expediente){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(expediente, ExpedienteDTO.class);
    }
}
