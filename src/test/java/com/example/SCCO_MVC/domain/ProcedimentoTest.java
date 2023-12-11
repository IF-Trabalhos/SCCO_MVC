package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcedimentoTest {

    @Test
    void shouldValidateProcedimentoWithValidData() {
        Especialidade especialidade =
                new Especialidade("Especialidade Teste", true);
        Procedimento procedimento =
                new Procedimento("Procedimento Teste", true, 100.0, especialidade);

        assertDoesNotThrow(procedimento::validate);
    }

    @Test
    void shouldThrowExceptionForEmptyNome() {
        Especialidade especialidade = new Especialidade("Especialidade Teste", true);
        Procedimento procedimento = new Procedimento("", true, 100.0, especialidade);

        assertThrows(RegraNegocioException.class, procedimento::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidNomeSize() {
        Especialidade especialidade = new Especialidade("Especialidade Teste", true);
        Procedimento procedimento = new Procedimento(null, true, 100.0, especialidade);

        assertThrows(RegraNegocioException.class, procedimento::validate);
    }

    @Test
    void shouldThrowExceptionForExceededNomeSize() {
        Especialidade especialidade =
                new Especialidade("Especialidade Teste", true);
        Procedimento procedimento =
                new Procedimento("A".repeat(256), true, 100.0, especialidade);

        assertThrows(RegraNegocioException.class, procedimento::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidValor() {
        Especialidade especialidade =
                new Especialidade("Especialidade Teste", true);
        Procedimento procedimento =
                new Procedimento("Procedimento Teste", true, -100.0, especialidade);

        assertThrows(RegraNegocioException.class, procedimento::validate);
    }

    @Test
    void shouldThrowExceptionForNullEspecialidade() {
        Procedimento procedimento =
                new Procedimento("Procedimento Teste", true, 100.0, null);

        assertThrows(RegraNegocioException.class, procedimento::validate);
    }

    @Test
    void shouldThrowExceptionForNullStatus() {
        Especialidade especialidade =
                new Especialidade("Especialidade Teste", null);
        Procedimento procedimento =
                new Procedimento("Procedimento Teste", null, 100.0, especialidade);

        assertThrows(RegraNegocioException.class, procedimento::validate);
    }
}
