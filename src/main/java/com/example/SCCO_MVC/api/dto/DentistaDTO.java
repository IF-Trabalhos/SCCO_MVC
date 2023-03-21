package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Dentista;
import org.modelmapper.ModelMapper;

public class DentistaDTO {
    private Long id;
    private String cro;
    private String nome;
    private Long enderecoId;

    public static DentistaDTO create(Dentista dentista){
        ModelMapper modelMapper = new ModelMapper();
        DentistaDTO dto = modelMapper.map(dentista, DentistaDTO.class);
        return dto;
    }
}
