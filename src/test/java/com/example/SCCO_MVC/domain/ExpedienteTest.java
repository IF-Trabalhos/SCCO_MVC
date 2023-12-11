package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class ExpedienteTest {

    @Test
    void shouldValidateExpedienteWithValidData() {
        Expediente expediente = new Expediente("Segunda-feira", Time.valueOf("08:00:00"), Time.valueOf("17:00:00"));
        assertDoesNotThrow(expediente::validate);
    }

    @Test
    void shouldThrowExceptionForNullHoraInicial() {
        Expediente expediente = new Expediente("Segunda-feira", null, Time.valueOf("17:00:00"));
        assertThrows(RegraNegocioException.class, expediente::validate);
    }

    @Test
    void shouldThrowExceptionForNullHoraFinal() {
        Expediente expediente = new Expediente("Segunda-feira", Time.valueOf("08:00:00"), null);
        assertThrows(RegraNegocioException.class, expediente::validate);
    }

    @Test
    void shouldThrowExceptionForHoraInicialAfterHoraFinal() {
        Expediente expediente = new Expediente("Segunda-feira", Time.valueOf("18:00:00"), Time.valueOf("07:00:00"));
        assertThrows(RegraNegocioException.class, expediente::validate);
    }

    @Test
    void shouldThrowExceptionForHoraInicialBefore0700() {
        Expediente expediente = new Expediente("Segunda-feira", Time.valueOf("06:59:59"), Time.valueOf("17:00:00"));
        assertThrows(RegraNegocioException.class, expediente::validate);
    }

    @Test
    void shouldThrowExceptionForHoraFinalAfter1800() {
        Expediente expediente = new Expediente("Segunda-feira", Time.valueOf("08:00:00"), Time.valueOf("18:00:01"));
        assertThrows(RegraNegocioException.class, expediente::validate);
    }

    @Test
    void shouldThrowExceptionForNullDiaDaSemana() {
        Expediente expediente = new Expediente(null, Time.valueOf("08:00:00"), Time.valueOf("17:00:00"));
        assertThrows(RegraNegocioException.class, expediente::validate);
    }

    @Test
    void shouldThrowExceptionForEmptyDiaDaSemana() {
        Expediente expediente = new Expediente("", Time.valueOf("08:00:00"), Time.valueOf("17:00:00"));
        assertThrows(RegraNegocioException.class, expediente::validate);
    }
}
