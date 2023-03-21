package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.model.entity.Secretaria;
import org.modelmapper.ModelMapper;

public class SecretariaDTO {
    private Long id;
    private String nome;
    private double salario;
    private String pis;
    public static SecretariaDTO create(Secretaria secretatia){
        ModelMapper modelMapper = new ModelMapper();
        SecretariaDTO dto = modelMapper.map(secretatia, SecretariaDTO.class);
        return dto;
    }
}
