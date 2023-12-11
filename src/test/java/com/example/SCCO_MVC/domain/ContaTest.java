package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContaTest {

    @Test
    void shouldValidateContaWithValidData() {
        Consulta consulta = new Consulta();
        Paciente paciente = new Paciente();
        Conta conta = new Conta(LocalDate.now(), 500.0, consulta, paciente);

        assertDoesNotThrow(conta::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidConsulta() {
        Paciente paciente = new Paciente();
        Conta conta = new Conta(LocalDate.now(), 500.0, null, paciente);

        assertThrows(RegraNegocioException.class, conta::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidPaciente() {
        Consulta consulta = new Consulta();
        Conta conta = new Conta(LocalDate.now(), 500.0, consulta, null);

        assertThrows(RegraNegocioException.class, conta::validate);
    }
}
