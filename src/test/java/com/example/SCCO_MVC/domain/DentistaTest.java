package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DentistaTest {

    @Test
    void shouldValidateDentistaWithValidData() {
        Dentista dentista = new Dentista("1234567", new Especialidade(), new ArrayList<>());
        dentista.setNome("Nome");
        dentista.setCpf("123.456.789-00");
        dentista.setTelefone("12345678");
        dentista.setDataDeNascimento(LocalDate.of(1990, 1, 1));
        dentista.setRg("123456789");
        dentista.setEmail("email@example.com");
        dentista.setAtivo(true);
        dentista.setEndereco(new Endereco());

        assertDoesNotThrow(dentista::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidEspecialidade() {
        Dentista dentista = new Dentista("1234567", null, new ArrayList<>());
        assertThrows(RegraNegocioException.class, dentista::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidCroLength() {
        Dentista dentista = new Dentista("123456", new Especialidade(), new ArrayList<>());
        assertThrows(RegraNegocioException.class, dentista::validate);
    }

    @Test
    void shouldThrowExceptionForInvalidCroDigits() {
        Dentista dentista = new Dentista("1a34567", new Especialidade(), new ArrayList<>());
        assertThrows(RegraNegocioException.class, dentista::validate);
    }

    @Test
    void shouldThrowExceptionForDentistaUnder18YearsOld() {
        Dentista dentista = new Dentista("1234567", new Especialidade(), new ArrayList<>());
        dentista.setDataDeNascimento(LocalDate.now().minusYears(17));
        assertThrows(RegraNegocioException.class, dentista::validate);
    }

    @Test
    void shouldCalculateEarningsForDentist() {
        Consulta consulta1 = new Consulta(300.0, new Paciente(), new Dentista(),
                new ArrayList<>(), new Agenda());
        Consulta consulta2 = new Consulta(500.0, new Paciente(), new Dentista(),
                new ArrayList<>(), new Agenda());
        List<Consulta> consultas = List.of(consulta1, consulta2);

        Dentista dentista =
                new Dentista("1234567", new Especialidade("Ortodontia",true), new ArrayList<>());
        double expectedEarnings = (300.0 * 0.65) + (500.0 * 0.65);

        assertEquals(expectedEarnings, dentista.calculateEarnings(consultas));
    }
}
