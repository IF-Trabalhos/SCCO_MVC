package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Procedimento;
import org.modelmapper.ModelMapper;

public class ProcedimentoDTO {
    private Long id;
    private String nome;
    private boolean status;
    private double valor;

    private Long especialidadeId;
    private Long tratamentoId;

    public static ProcedimentoDTO create(Procedimento procedimento){
        ModelMapper modelMapper = new ModelMapper();
        ProcedimentoDTO dto = modelMapper.map(procedimento, ProcedimentoDTO.class);
        return dto;
    }
}
