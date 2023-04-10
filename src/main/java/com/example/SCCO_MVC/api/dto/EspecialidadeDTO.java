package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Especialidade;
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
    private boolean status;

    public static EspecialidadeDTO create(Especialidade especialidade){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(especialidade, EspecialidadeDTO.class);
    }
}
