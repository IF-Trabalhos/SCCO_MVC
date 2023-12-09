package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.PacienteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente extends Pessoa {
    private String numProntuario;
    private Convenio convenio;

    public void validate() {
        super.validate();
        if (this.getNumProntuario() == null) {
            throw new RegraNegocioException("Numero de Prontuario vazio ou invalido");
        }
        if (this.getNumProntuario().trim().equals("")){
            throw new RegraNegocioException("Numero de Prontuario vazio ou invalido");
        }
        if (this.getNumProntuario().length() > 14) {
            throw new RegraNegocioException("Numero de Prontuario vazio ou invalido");
        }
        if (this.getDataDeNascimento().getYear() > LocalDate.now().minusMonths(6).getYear()){
            throw new RegraNegocioException("Data de nascimento inválida, paciente recém nascido");
        }
        if (this.getDataDeNascimento().getYear() == LocalDate.now().minusMonths(6).getYear()
                && this.getDataDeNascimento().getMonthValue() > LocalDate.now().minusMonths(6).getMonthValue()) {
            throw new RegraNegocioException("Data de nascimento inválida, paciente com menos de 6 meses");
        }
    }

    public Double calculateCosts(List<Consulta> consultas) {
        double costs = 0;
        for (Consulta consulta : consultas){
            costs += consulta.getValorConsulta();
        }
        return costs;
    }
}
