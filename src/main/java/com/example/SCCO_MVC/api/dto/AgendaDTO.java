package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Agenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaDTO {
    private Long id;
    private Double faturamento;
    private Long disponibilidadeId;

    public static AgendaDTO create(Agenda agenda){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agenda, AgendaDTO.class);
    }
}
