package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConvenioTest {

    @Test
    void shouldValidateConvenioWithValidData() {
        Convenio convenio = new Convenio("Nome Convenio", "12345678", "email@test.com", "1234567890");

        assertDoesNotThrow(convenio::validate);
    }

    @Test
    void shouldThrowExceptionForEmptyNome() {
        Convenio convenio = new Convenio(null, "12345678", "email@test.com", "1234567890");

        assertThrows(RegraNegocioException.class, convenio::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidEmailSize() {
        Convenio convenio =
                new Convenio("Nome Convenio", "12345678",
                        "a".repeat(151), "1234567890");

        assertThrows(RegraNegocioException.class, convenio::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidRegistroAnsSize() {
        Convenio convenio =
                new Convenio("Nome Convenio", "1234",
                        "email@test.com", "1234567890");

        assertThrows(RegraNegocioException.class, convenio::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidTelefoneSize() {
        Convenio convenio =
                new Convenio("Nome Convenio", "12345678",
                        "email@test.com", "1234567890123456");

        assertThrows(RegraNegocioException.class, convenio::validate);
    }
}
