package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ExpedienteTest {
    Expediente validExpediente =
            new Expediente(
                   "Terça",
                   Time.valueOf(LocalTime.of(7,1)),
                   Time.valueOf(LocalTime.of(17,59))
            );
    Expediente expediente;

    @BeforeEach
    void setUp() {
        expediente = validExpediente;
    }
    @Test
    void shouldReturnValidExpediente() {
        assertDoesNotThrow(() ->
                expediente.validate());
    }
    @Test
    void shouldReturnInvalidHoraInicialNull() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () ->{
                    expediente.setHoraInicial(null);
                    expediente.validate();
                });
        assertEquals("Horário inicial ou final inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraFinalNull() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () ->{
                    expediente.setHoraFinal(null);
                    expediente.validate();
                });
        assertEquals("Horário inicial ou final inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraInicialAftereHoraFinal() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    expediente.setHoraInicial(Time.valueOf(LocalTime.of(15, 0)));
                    expediente.setHoraFinal(Time.valueOf(LocalTime.of(14, 0)));
                    expediente.validate();
                });
        assertEquals("Hora inicial não pode ser posterior à hora final", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraInicialBefore7am() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () ->{
                    expediente.setHoraInicial(Time.valueOf(LocalTime.of(6, 59)));
                    expediente.validate();
                });
        assertEquals("O expediente deve estar no intervalo de 07:00 às 18:00", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidHoraFinalAfter6pm() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () ->{
                    expediente.setHoraFinal(Time.valueOf(LocalTime.of(18, 1)));
                    expediente.validate();
                });
        assertEquals("O expediente deve estar no intervalo de 07:00 às 18:00", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidDiaDaSemanaNull() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () ->{
                    expediente.setDiaDaSemana(null);
                    expediente.validate();
                });
        assertEquals("Dia da semana inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidDiaDaSemanaEmpty() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () ->{
                    expediente.setDiaDaSemana("");
                    expediente.validate();
                });
        assertEquals("Dia da semana inválido", exception.getMessage());
    }

}
