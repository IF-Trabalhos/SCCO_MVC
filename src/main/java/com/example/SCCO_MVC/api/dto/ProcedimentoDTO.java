package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Procedimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcedimentoDTO {
    private Long id;
    private String nome;
    private boolean status;
    private double valor;

    private Long especialidadeId;
    private Long tratamentoId;

    public static ProcedimentoDTO create(Procedimento procedimento){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(procedimento, ProcedimentoDTO.class);
    }
}
