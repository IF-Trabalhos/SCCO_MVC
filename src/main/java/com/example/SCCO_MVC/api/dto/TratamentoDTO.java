package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Tratamento;
import org.modelmapper.ModelMapper;

public class TratamentoDTO {
    private Long id;

    public static TratamentoDTO create(Tratamento tratamento){
        ModelMapper modelMapper = new ModelMapper();
        TratamentoDTO dto = modelMapper.map(tratamento, TratamentoDTO.class);
        return dto;
    }
}