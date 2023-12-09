package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Procedimento;
import com.example.SCCO_MVC.model.entity.ProcedimentoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimentoDTO {
    private Long id;
    private String nome;
    private Boolean status;
    private Double valor;
    private EspecialidadeDTO especialidadeDTO;

    public static ProcedimentoDTO fromEntityToDTO(ProcedimentoEntity procedimentoEntity){
        ModelMapper modelMapper = new ModelMapper();
        ProcedimentoDTO dto = modelMapper.map(procedimentoEntity, ProcedimentoDTO.class);
        dto.setEspecialidadeDTO(EspecialidadeDTO.fromEntityToDTO(procedimentoEntity.getEspecialidadeEntity()));

        return dto;
    }

    public Procedimento fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();
        Procedimento procedimento = modelMapper.map(this, Procedimento.class);
        procedimento.setEspecialidade(this.getEspecialidadeDTO().fromDTOToDomain());
        return procedimento;
    }

    public ProcedimentoEntity fromDTOToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        ProcedimentoEntity procedimentoEntity = modelMapper.map(this, ProcedimentoEntity.class);
        procedimentoEntity.setEspecialidadeEntity(this.getEspecialidadeDTO().fromDTOToEntity());
        return procedimentoEntity;
    }
}
