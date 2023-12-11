package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ConsultaTest {
    Consulta validConsulta = new Consulta(
            300.0,
            new Paciente(),
            new Dentista(),
            (List<Procedimento>) new ArrayList(),
            new Agenda()
    );

    Consulta consulta;

    @BeforeEach
    public void init() {
        consulta = validConsulta;
    }

    @Test
    void shouldReturnValidConsulta() {
        assertDoesNotThrow(() ->
                consulta.validate()
        );
    }

    @Test
    void shouldReturnInvalidConsulta() {
        RegraNegocioException exception
                = assertThrows(RegraNegocioException.class, () -> {
            consulta.setValorConsulta(-0.1);
            consulta.validate();
        });
        assertEquals("Valor da consulta invalido", exception.getMessage());
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

    @Test
    void shouldReturnInvalidAgenda() {
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            consulta.setAgenda(null);
            consulta.validate();
        });
        assertEquals("Agenda invalida", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidProcedimento() {
        List<Procedimento> test = new ArrayList();
        test.add(new Procedimento());
        test.add(null);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> {
            consulta.setProcedimentos(test);
            consulta.validate();
        });
        assertEquals("Procedimento invalido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEspecialidade() {
        List<Procedimento> test = new ArrayList();
        test.add(new Procedimento("Inválido",true,100.0 ,new Especialidade("Inválida", true)));
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    consulta.setProcedimentos(test);
                    consulta.validate();
                });
        assertEquals("Dentista com especialidade invalida", exception.getMessage());
    }
    @Test
    void shouldReturn15Percent() {
        Procedimento procedimento =
                new Procedimento("Limpeza Dental", true, 100.0, new Especialidade());

        assertEquals(0.15, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn25Percent() {
        Procedimento procedimento =
                new Procedimento("Extração de Dente", true, 100.0, new Especialidade());

        assertEquals(0.25, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn20Percent() {
        Procedimento procedimento =
                new Procedimento("Restauração Dentária", true, 100.0, new Especialidade());

        assertEquals(0.20, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn30Percent() {
        Procedimento procedimento =
                new Procedimento("Tratamento de Canal", true, 100.0, new Especialidade());

        assertEquals(0.30, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn40Percent() {
        Procedimento procedimento =
                new Procedimento("Implante Dentário", true, 100.0, new Especialidade());

        assertEquals(0.40, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn10Percent() {
        Procedimento procedimento =
                new Procedimento("Clareamento Dental", true, 100.0, new Especialidade());

        assertEquals(0.10, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn35Percent() {
        Procedimento procedimento =
                new Procedimento("Ortodontia", true, 100.0, new Especialidade());
        assertEquals(0.35, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn50Percent() {
        Procedimento procedimento =
                new Procedimento("Prostodontia", true, 100.0, new Especialidade());
        assertEquals(0.50, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn20_2Percent() {
        Procedimento procedimento =
                new Procedimento("Cirurgia Bucomaxilofacial", true, 100.0, new Especialidade());
        assertEquals(0.20, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldReturn15_2Percent() {
        Procedimento procedimento =
                new Procedimento("Periodontia", true, 100.0, new Especialidade());
        assertEquals(0.15, consulta.calculateDiscountConvenience(procedimento));
    }

    @Test
    void shouldSetConsultValueWithConvenioDiscount() {
        List<Procedimento> procedimentos =
                new ArrayList<>();
        Procedimento procedimento =
                new Procedimento("Limpeza Dental", true, 100.0, new Especialidade());
        Convenio convenio =
                new Convenio("Limpeza", "01", "limpeza@gmail.com", "32999999999");
        Paciente paciente = new Paciente("1", convenio);
        consulta.setPaciente(paciente);
        consulta.setProcedimentos(procedimentos);
        consulta.calculateConsultValue();
        assertEquals(300.0, consulta.getValorConsulta());
    }

    @Test
    void shouldSetConsultValueWithoutConvenioDiscount() {
        List<Procedimento> procedimentos =
                new ArrayList<>();
        Procedimento procedimento =
                new Procedimento("Limpeza Dental", true, 100.0, new Especialidade());
        consulta.setProcedimentos(procedimentos);
        consulta.calculateConsultValue();
        assertEquals(300.0, consulta.getValorConsulta());
    }
}