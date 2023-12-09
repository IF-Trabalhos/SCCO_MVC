package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dentista extends Pessoa {
    private String cro;
    private Especialidade especialidade;
    private List<Expediente> expedientes;

    public void validate() {
        super.validate();
        if (this.getEspecialidade() == null) {
            throw new RegraNegocioException("Especialidade inválida");
        }
        if (this.getCro().length() != 7) {
            throw new RegraNegocioException("Cro inválido, diferente de 7 dígitos");
        }
        if (!this.getCro().matches("[0-9]+")) {
            throw new RegraNegocioException("CRO inválido, deve conter apenas números");
        }
        if (this.getDataDeNascimento().getYear() > LocalDate.now().minusYears(18).getYear()) {
            throw new RegraNegocioException("Data de nascimento inválida, dentista menor de idade");
        }
    }

    public Double calculateEarnings(List<Consulta> consultas) {
        double salary = 0;
        for (Consulta consulta : consultas) {
            double comissao = calculateDentistCommission();
            salary += consulta.getValorConsulta() * comissao;
        }
        return salary;
    }

    private Double calculateDentistCommission() {
        double comissao = 0;
        if (this.getEspecialidade().getNome().equals("Ortodontia")) {
            comissao = 0.65;
        } else if (this.getEspecialidade().getNome().equals("Cirurgia Bucomaxilofacial")) {
            comissao = 0.70;
        } else if (this.getEspecialidade().getNome().equals("Implante Dentário")) {
            comissao = 0.60;
        } else if (this.getEspecialidade().getNome().equals("Periodontia")) {
            comissao = 0.50;
        } else if (this.getEspecialidade().getNome().equals("Clareamento Dental")) {
            comissao = 0.40;
        } else {
            comissao = 0.60;
        }
        return comissao;
    }
}
