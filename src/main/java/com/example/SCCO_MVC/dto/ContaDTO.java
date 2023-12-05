package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Conta;
import com.example.SCCO_MVC.model.entity.ContaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {
    private Long id;

    private LocalDate dataEmissao;
    private Double valorTotal;
    private ConsultaDTO consultaDTO;
    private PacienteDTO pacienteDTO;

    public static ContaDTO fromEntityToDTO(ContaEntity contaEntity){
        ModelMapper modelMapper = new ModelMapper();
        ContaDTO dto = modelMapper.map(contaEntity, ContaDTO.class);

        dto.setConsultaDTO(ConsultaDTO.fromEntityToDTO(contaEntity.getConsultaEntity()));
        dto.setPacienteDTO(PacienteDTO.fromEntityToDTO(contaEntity.getPacienteEntity()));

        return dto;
    }

    public Conta fromDTOToDomain(){
        ModelMapper modelMapper = new ModelMapper();
        Conta conta = modelMapper.map(this, Conta.class);

        conta.setConsulta(this.getConsultaDTO().fromDTOToDomain());
        conta.setPaciente(this.getPacienteDTO().fromDTOToDomain());

        return conta;
    }

    public ContaEntity fromDTOToEntity(){
        ModelMapper modelMapper = new ModelMapper();
        ContaEntity contaEntity = modelMapper.map(this, ContaEntity.class);

        contaEntity.setConsultaEntity(this.getConsultaDTO().fromDTOToEntity());
        contaEntity.setPacienteEntity(this.getPacienteDTO().fromDTOToEntity());

        return contaEntity;
    }
}
