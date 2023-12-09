package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {
    private LocalDate data;
    private Time horaInicial;
    private Time horaFinal;

    public void validate() {
        if (this.getData() == null) {
            throw new RegraNegocioException("Data inválida ou anterior à data atual");
        }
        if (this.getData().isBefore(LocalDate.now())){
            throw new RegraNegocioException("Data inválida ou anterior à data atual");
        }
        if (this.getHoraInicial() == null) {
            throw new RegraNegocioException("Horário inicial ou final inválido");
        }
        if (this.getHoraFinal() == null){
            throw new RegraNegocioException("Horário inicial ou final inválido");
        }

        LocalTime horaInicialLocalTime = this.getHoraInicial().toLocalTime();
        LocalTime horaFinalLocalTime = this.getHoraFinal().toLocalTime();

        if (horaInicialLocalTime.isBefore(LocalTime.of(7, 0))) {
            throw new RegraNegocioException("O horário deve ser entre 7:00 e 17:00");
        }
        if (horaInicialLocalTime.isAfter(LocalTime.of(17, 0))){
            throw new RegraNegocioException("O horário deve ser entre 7:00 e 17:00");
        }
        if (horaFinalLocalTime.isBefore(LocalTime.of(8, 0))) {
            throw new RegraNegocioException("O horário deve ser entre 8:00 e 18:00");
        }
        if (horaFinalLocalTime.isAfter(LocalTime.of(18, 0))){
            throw new RegraNegocioException("O horário deve ser entre 8:00 e 18:00");
        }
        if (horaInicialLocalTime.isAfter(horaFinalLocalTime)) {
            throw new RegraNegocioException("Horário de início após o horário final");
        }
    }
}
