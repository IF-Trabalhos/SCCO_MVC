package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Especialidade;
import org.modelmapper.ModelMapper;

public class EspecialidadeDTO {
    private Long id;
    private String nome;
    private boolean status;

    public static EspecialidadeDTO create(Especialidade especialidade){
        ModelMapper modelMapper = new ModelMapper();
        EspecialidadeDTO dto = modelMapper.map(especialidade, EspecialidadeDTO.class);
        return dto;
    }
}
