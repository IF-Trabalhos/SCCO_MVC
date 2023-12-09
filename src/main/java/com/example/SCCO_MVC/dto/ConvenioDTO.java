package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Convenio;
import com.example.SCCO_MVC.model.entity.ConvenioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConvenioDTO {
    private Long id;
    private String nome;
    private String registroAns;
    private String email;
    private String telefone;
    private Double desconto;

    public static ConvenioDTO fromEntityToDTO(ConvenioEntity convenioEntity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(convenioEntity, ConvenioDTO.class);
    }

    public Convenio fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Convenio.class);
    }

    public ConvenioEntity fromDTOToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConvenioEntity.class);
    }
}
