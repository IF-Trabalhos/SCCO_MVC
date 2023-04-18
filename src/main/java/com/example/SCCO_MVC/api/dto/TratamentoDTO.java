package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Tratamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TratamentoDTO {
    private Long id;

    public static TratamentoDTO create(Tratamento tratamento){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(tratamento, TratamentoDTO.class);
    }
}