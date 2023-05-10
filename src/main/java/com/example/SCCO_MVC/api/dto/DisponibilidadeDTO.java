package com.example.SCCO_MVC.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadeDTO {
    private Long id;
    private Time horaInicialIntervalo;
    private Time horaFinalIntervalo;

    public static DisponibilidadeDTO create(Disponibilidade disponibilidade){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(disponibilidade, DisponibilidadeDTO.class);
    }
}

