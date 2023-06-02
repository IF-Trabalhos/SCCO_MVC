package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Convenio;
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

    public static ConvenioDTO create(Convenio convenio){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(convenio, ConvenioDTO.class);
    }
}
