package com.example.SCCO_MVC.domain;

import java.sql.Time;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.ExpedienteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expediente {
    private String diaDaSemana;
    private Time horaInicial;
    private Time horaFinal;

    public void validate(){
        if (this.getHoraInicial() == null) {
            throw new RegraNegocioException("Hora inicial invalida");
        }
        if (this.getHoraFinal() == null) {
            throw new RegraNegocioException("Hora final invalida");
        }
    }
}
