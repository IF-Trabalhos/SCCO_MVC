package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ConsultaTest {
    Consulta validConsulta = new Consulta(
            300.0,
            new Paciente(),
            new Dentista(),
            (List<Procedimento>) new Procedimento(),
            new Agenda()
    );

    Consulta consulta;

    @BeforeEach
    public void init(){
        consulta = validConsulta;
    }

    @Test
    void shouldReturnValidConsulta() {
        assertDoesNotThrow(() ->
                consulta.validate()
        );
    }

    @Test
    void shouldReturnInvalidDentist() {
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            consulta.setDentista(null);
            consulta.validate();
        });
        assertEquals("Dentista invalido", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidPatient() {
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            consulta.setPaciente(null);
            consulta.validate();
        });
        assertEquals("Paciente invalido", exception.getMessage());
    }
}
