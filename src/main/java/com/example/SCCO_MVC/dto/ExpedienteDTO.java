package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Expediente;
import com.example.SCCO_MVC.model.entity.ExpedienteEntity;
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

    public static ExpedienteDTO fromEntityToDTO(ExpedienteEntity expedienteEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(expedienteEntity, ExpedienteDTO.class);
    }

    public Expediente fromDTOToDomain(){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Expediente.class);
    }

    public ExpedienteEntity fromDTOToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ExpedienteEntity.class);
    }
}
