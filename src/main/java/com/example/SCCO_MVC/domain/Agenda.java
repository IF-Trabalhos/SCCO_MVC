package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {
    private LocalDate data;
    private Time horaInicial;
    private Time horaFinal;

    public void validate(){
        if (this.getData() == null){
            throw new RegraNegocioException("Data invalida");
        }
        if (this.getHoraInicial() == null) {
            throw new RegraNegocioException("Hora inicial invalida");
        }
        if (this.getHoraFinal() == null) {
            throw new RegraNegocioException("Hora final invalida");
        }
        if (this.horaInicial.after(this.horaFinal)) {
            throw new RegraNegocioException("Data de inicio depois da data inicial");
        }
    }
}
