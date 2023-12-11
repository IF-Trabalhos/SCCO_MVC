package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AgendaTest {
    Agenda validAgenda =
            new Agenda(
                    LocalDate.now().plusDays(3),
                    Time.valueOf(LocalTime.now().minusHours(2)),
                    Time.valueOf(LocalTime.now())
            );


    Agenda agenda;

    @BeforeEach
    void setUp() {
        agenda = validAgenda;
    }
    @Test
    void shouldReturnValidAgenda() {
        assertDoesNotThrow(() ->
                agenda.validate());
    }
    @Test
    void shouldReturnInvalidDataNull() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setData(null);
                    agenda.validate();
                });
        assertEquals("Data inválida ou anterior à data atual", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidDataBeforeNow() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setData(LocalDate.now().minusDays(1));
                    agenda.validate();
                });
        assertEquals("Data inválida ou anterior à data atual", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraInicialNull() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setHoraInicial(null);
                    agenda.validate();
                });
        assertEquals("Horário inicial inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraFinalNull() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setHoraFinal(null);
                    agenda.validate();
                });
        assertEquals("Horário Final inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraInicialBefore7am() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setHoraInicial(Time.valueOf(LocalTime.of(6,59)));
                    agenda.validate();
                });
        assertEquals("O horário deve ser entre 7:00 e 17:00", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraInicialAfter5pm() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setHoraInicial(Time.valueOf(LocalTime.of(17,01)));
                    agenda.validate();
                });
        assertEquals("O horário deve ser entre 7:00 e 17:00", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraFinalBefore8am() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setHoraFinal(Time.valueOf(LocalTime.of(7,59)));
                    agenda.validate();
                });
        assertEquals("O horário deve ser entre 8:00 e 18:00", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraFinalAfter6pm() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setHoraFinal(Time.valueOf(LocalTime.of(18,01)));
                    agenda.validate();
                });
        assertEquals("O horário deve ser entre 8:00 e 18:00", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraInicialAfterHoraFinal() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    agenda.setHoraInicial(Time.valueOf(LocalTime.of(15,0)));
                    agenda.setHoraFinal(Time.valueOf(LocalTime.of(14,0)));
                    agenda.validate();
                });
        assertEquals("Horário de início após o horário final", exception.getMessage());
    }
}