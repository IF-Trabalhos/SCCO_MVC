package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.sql.Time;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expediente {
    private String diaDaSemana;
    private Time horaInicial;
    private Time horaFinal;

    public void validate() {
        if (this.getHoraInicial() == null || this.getHoraFinal() == null) {
            throw new RegraNegocioException("Horário inicial ou final inválido");
        }

        if (this.horaInicial.after(this.horaFinal)) {
            throw new RegraNegocioException("Hora inicial não pode ser posterior à hora final");
        }

        if (this.horaInicial.before(Time.valueOf(LocalTime.of(7,0))) ||
                this.horaFinal.after(Time.valueOf(LocalTime.of(18,0)))) {
            throw new RegraNegocioException("O expediente deve estar no intervalo de 07:00 às 18:00");
        }

        if (this.diaDaSemana == null || this.diaDaSemana.trim().isEmpty()) {
            throw new RegraNegocioException("Dia da semana inválido");
        }
    }
}
