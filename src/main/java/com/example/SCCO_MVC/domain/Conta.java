package com.example.SCCO_MVC.domain;


import java.time.LocalDate;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    private LocalDate dataEmissao;
    private Double valorTotal;
    private Consulta consulta;
    private Paciente paciente;

    public void validate() {
        if (this.consulta == null ) {
            throw new RegraNegocioException("Consulta invalida");
        }
        if (this.paciente == null ) {
            throw new RegraNegocioException("Paciente invalido");
        }
    }

}
