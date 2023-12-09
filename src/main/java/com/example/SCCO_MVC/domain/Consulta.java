package com.example.SCCO_MVC.domain;
import com.example.SCCO_MVC.exception.RegraNegocioException;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    private Double valorConsulta;
    private Paciente paciente;
    private Dentista dentista;
    private List<Procedimento> procedimentos;
    private Agenda agenda;

    public void validate() {
        if (this.dentista == null) {
            throw new RegraNegocioException("Dentista invalido");
        }
        if (this.paciente == null) {
            throw new RegraNegocioException("Paciente invalido");
        }
    }
}
