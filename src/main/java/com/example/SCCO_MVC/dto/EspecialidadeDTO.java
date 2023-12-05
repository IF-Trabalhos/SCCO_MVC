package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Especialidade;
import com.example.SCCO_MVC.model.entity.EspecialidadeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeDTO {
    private Long id;
    private String nome;
    private Boolean status;

    public static EspecialidadeDTO fromEntityToDTO(EspecialidadeEntity especialidadeEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(especialidadeEntity, EspecialidadeDTO.class);
    }

    public Especialidade fromDTOToDomain(){
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(this, Especialidade.class);
    }

    public EspecialidadeEntity fromDTOToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EspecialidadeEntity.class);
    }
}
