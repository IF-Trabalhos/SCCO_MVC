package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ContaTest {
    Conta validConta = new Conta(
            LocalDate.now(),
            300.0,
            new Consulta(),
            new Paciente()
    );

    Conta conta;

    @BeforeEach
    public void init() {
        conta = validConta;
    }

    @Test
    void shouldReturnValidConta() {
        assertDoesNotThrow(() ->
                conta.validate()
        );
    }


    @Test
    void shouldReturnInvalidDentist() {
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            conta.setConsulta(null);
            conta.validate();
        });
        assertEquals("Consulta invalida", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidPatient() {
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            conta.setPaciente(null);
            conta.validate();
        });
        assertEquals("Paciente invalido", exception.getMessage());
    }
}
