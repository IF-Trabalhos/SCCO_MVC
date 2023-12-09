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
    private PacienteDTO pacienteDTO;
    private DentistaDTO dentistaDTO;
    private List<ProcedimentoDTO> procedimentosDTO;
    private AgendaDTO agendaDTO;

    public static ConsultaDTO fromEntityToDTO(ConsultaEntity consultaEntity) {
        ModelMapper modelMapper = new ModelMapper();
        ConsultaDTO consultaDTO = modelMapper.map(consultaEntity, ConsultaDTO.class);

        consultaDTO.setPacienteDTO(PacienteDTO.fromEntityToDTO(consultaEntity.getPacienteEntity()));
        consultaDTO.setDentistaDTO(DentistaDTO.fromEntityToDTO(consultaEntity.getDentistaEntity()));
        consultaDTO.setProcedimentosDTO(consultaEntity.getProcedimentos()
                .stream()
                .map(ProcedimentoDTO::fromEntityToDTO)
                .collect(Collectors.toList()));
        consultaDTO.setAgendaDTO(AgendaDTO.fromEntityToDTO(consultaEntity.getAgendaEntity()));

        return consultaDTO;
    }

    public Consulta fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();
        Consulta consulta = modelMapper.map(this, Consulta.class);

        consulta.setPaciente(this.getPacienteDTO().fromDTOToDomain());
        consulta.setDentista(this.getDentistaDTO().fromDTOToDomain());
        consulta.setProcedimentos(this.getProcedimentosDTO()
                .stream()
                .map(ProcedimentoDTO::fromDTOToDomain)
                .collect(Collectors.toList()));
        consulta.setAgenda(this.getAgendaDTO().fromDTOToDomain());

        return consulta;
    }

    public ConsultaEntity fromDTOToEntity(){
        ModelMapper modelMapper = new ModelMapper();
        ConsultaEntity consultaEntity = modelMapper.map(this, ConsultaEntity.class);

        consultaEntity.setPacienteEntity(this.getPacienteDTO().fromDTOToEntity());
        consultaEntity.setDentistaEntity(this.getDentistaDTO().fromDTOToEntity());
        consultaEntity.setProcedimentos(this.getProcedimentosDTO()
                .stream()
                .map(ProcedimentoDTO::fromDTOToEntity)
                .collect(Collectors.toList()));
        consultaEntity.setAgendaEntity(this.getAgendaDTO().fromDTOToEntity());

        return consultaEntity;
    }
}
