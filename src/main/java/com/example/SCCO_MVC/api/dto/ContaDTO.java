package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Conta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {
    private Long id;

    private LocalDate dataEmissao;
    private Double valorTotal;
    private Long consultaId;
    private Long pacienteId;

    public static ContaDTO create(Conta conta){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(conta, ContaDTO.class);
    }
}
