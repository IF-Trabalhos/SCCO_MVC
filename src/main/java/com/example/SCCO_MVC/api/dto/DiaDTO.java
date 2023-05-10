package com.example.SCCO_MVC.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
