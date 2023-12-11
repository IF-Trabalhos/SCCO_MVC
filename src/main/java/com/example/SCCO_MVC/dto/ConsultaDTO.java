package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Consulta;
import com.example.SCCO_MVC.model.entity.ConsultaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Long> procedimentosId;
    private Long agendaId;

    public static ConsultaDTO fromEntityToDTO(ConsultaEntity consultaEntity) {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(consultaEntity, ConsultaDTO.class);
    }

    public Consulta fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(this, Consulta.class);
    }

    public ConsultaEntity fromDTOToEntity(){
        ModelMapper modelMapper = new ModelMapper();


        return modelMapper.map(this, ConsultaEntity.class);
    }
}
