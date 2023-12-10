package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    @Test
    void shouldValidatePacienteWithValidData() {
        Paciente paciente = new Paciente();
        paciente.setNome("Nome");
        paciente.setCpf("123.456.789-00");
        paciente.setTelefone("12345678");
        paciente.setDataDeNascimento(LocalDate.of(1990, 1, 1));
        paciente.setRg("123456789");
        paciente.setEmail("email@example.com");
        paciente.setAtivo(true);
        paciente.setEndereco(new Endereco());
        paciente.setNumProntuario("12345678901234");

        assertDoesNotThrow(paciente::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidNumProntuarioNull() {
        Paciente paciente = new Paciente();
        assertThrows(RegraNegocioException.class, paciente::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidNumProntuarioEmpty() {
        Paciente paciente = new Paciente();
        paciente.setNumProntuario("");
        assertThrows(RegraNegocioException.class, paciente::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidNumProntuarioLength() {
        Paciente paciente = new Paciente();
        paciente.setNumProntuario("123456789012345678901234567890");
        assertThrows(RegraNegocioException.class, paciente::validate);
    }

    @Test
    void shouldThrowExceptionForPacienteUnder6MonthsOld() {
        Paciente paciente = new Paciente();
        paciente.setNumProntuario("12345678901234");
        paciente.setDataDeNascimento(LocalDate.now().minusMonths(5));
        assertThrows(RegraNegocioException.class, paciente::validate);
    }

    @Test
    void shouldThrowExceptionForPacienteWith6MonthsOrMore() {
        Paciente paciente = new Paciente();
        paciente.setNumProntuario("12345678901234");
        paciente.setDataDeNascimento(LocalDate.now().minusMonths(6));
        assertThrows(RegraNegocioException.class, paciente::validate);
    }

    @Test
    void shouldCalculateCostsForPaciente() {
        Consulta consulta1 =
                new Consulta(300.0, new Paciente(), new Dentista(), new ArrayList<>(), new Agenda());
        Consulta consulta2 = new Consulta(500.0, new Paciente(), new Dentista(), new ArrayList<>(), new Agenda());
        List<Consulta> consultas = List.of(consulta1, consulta2);

        Paciente paciente = new Paciente();
        double expectedCosts = 300.0 + 500.0;

        assertEquals(expectedCosts, paciente.calculateCosts(consultas));
    }
}
