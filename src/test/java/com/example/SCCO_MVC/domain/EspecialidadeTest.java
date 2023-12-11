package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EspecialidadeTest {

    @Test
    void shouldValidateEspecialidadeWithValidData() {
        Especialidade especialidade = new Especialidade("Especialidade Teste", true);

        assertDoesNotThrow(especialidade::validate);
    }

    @Test
    void shouldThrowExceptionForEmptyNome() {
        Especialidade especialidade = new Especialidade(null, true);

        assertThrows(RegraNegocioException.class, especialidade::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidNomeSize() {
        Especialidade especialidade = new Especialidade("", true);

        assertThrows(RegraNegocioException.class, especialidade::validate);
    }

    @Test
    void shouldThrowExceptionForExceededNomeSize() {
        Especialidade especialidade = new Especialidade("A".repeat(256), true);

        assertThrows(RegraNegocioException.class, especialidade::validate);
    }

    @Test
    void shouldThrowExceptionForNullStatus() {
        Especialidade especialidade = new Especialidade("Especialidade Teste", null);

        assertThrows(RegraNegocioException.class, especialidade::validate);
    }
}
