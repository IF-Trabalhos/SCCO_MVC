package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Paciente;
import org.modelmapper.ModelMapper;

public class PacienteDTO {
    private Long id;
    private String nome;
    private String numProntuario;
    private Long enderecoId;


    public static PacienteDTO create(Paciente paciente){
        ModelMapper modelMapper = new ModelMapper();
        PacienteDTO dto = modelMapper.map(paciente, PacienteDTO.class);
        return dto;
    }
}
