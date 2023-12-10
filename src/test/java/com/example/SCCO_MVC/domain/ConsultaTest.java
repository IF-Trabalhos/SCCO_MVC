package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.domain.*;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaTest {

    Consulta consulta;

    @BeforeEach
    void setUp() {
        consulta = new Consulta();
        consulta.setValorConsulta(100.0);

        Paciente pacienteValido = new Paciente();
        Dentista dentistaValido = new Dentista();
        Especialidade especialidade = new Especialidade();
        Agenda agendaValida = new Agenda();
        List<Procedimento> procedimentosValidos = new ArrayList<>();


        procedimentosValidos.add(new Procedimento());

        consulta.setPaciente(pacienteValido);
        consulta.setDentista(dentistaValido);
        consulta.setAgenda(agendaValida);
        consulta.setProcedimentos(procedimentosValidos);
    }

    @Test
    void shouldValidateConsultaWithValidData() {
        assertDoesNotThrow(() -> consulta.validate());
    }

    @Test
    void shouldThrowExceptionForInvalidConsultaValue() {
        consulta.setValorConsulta(-10.0);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> consulta.validate());
        assertEquals("Valor da consulta inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidDentist() {
        consulta.setDentista(null);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> consulta.validate());
        assertEquals("Dentista inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidPatient() {
        consulta.setPaciente(null);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> consulta.validate());
        assertEquals("Paciente inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidAgenda() {
        consulta.setAgenda(null);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> consulta.validate());
        assertEquals("Agenda inválida", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidProcedimento() {
        consulta.setProcedimentos(null);
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> consulta.validate());
        assertEquals("Procedimento inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidDentistSpeciality() {
        List<Procedimento> procedimentos = consulta.getProcedimentos();
        procedimentos.get(0).setEspecialidade(new Especialidade());
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, () -> consulta.validate());
        assertEquals("Dentista com especialidade inválida", exception.getMessage());
    }
    @Test
    void shouldCalculateDiscountForLimpezaDental() {
        Procedimento procedimento =
                new Procedimento("Limpeza Dental", true, 100.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.15, desconto);
    }

    @Test
    void shouldCalculateDiscountForExtracaoDeDente() {
        Procedimento procedimento =
                new Procedimento("Extração de Dente", true, 200.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.25, desconto);
    }

    @Test
    void shouldCalculateDiscountForRestauracaoDentaria() {
        Procedimento procedimento =
                new Procedimento("Restauração Dentária", true, 150.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.20, desconto);
    }

    @Test
    void shouldCalculateDiscountForTratamentoDeCanal() {
        Procedimento procedimento =
                new Procedimento("Tratamento de Canal",true ,300.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.30, desconto);
    }

    @Test
    void shouldCalculateDiscountForImplanteDentario() {
        Procedimento procedimento =
                new Procedimento("Implante Dentário",true, 400.0,new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.40, desconto);
    }

    @Test
    void shouldCalculateDiscountForClareamentoDental() {
        Procedimento procedimento =
                new Procedimento("Clareamento Dental",true, 120.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.10, desconto);
    }

    @Test
    void shouldCalculateDiscountForOrtodontia() {
        Procedimento procedimento =
                new Procedimento("Ortodontia", true,250.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.35, desconto);
    }

    @Test
    void shouldCalculateDiscountForProstodontia() {
        Procedimento procedimento = new Procedimento("Prostodontia",true ,500.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.50, desconto);
    }

    @Test
    void shouldCalculateDiscountForCirurgiaBucomaxilofacial() {
        Procedimento procedimento =
                new Procedimento("Cirurgia Bucomaxilofacial",true ,300.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.20, desconto);
    }

    @Test
    void shouldCalculateDiscountForPeriodontia() {
        Procedimento procedimento =
                new Procedimento("Periodontia",true ,180.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.15, desconto);
    }

    @Test
    void shouldCalculateDefaultDiscount() {
        Procedimento procedimento =
                new Procedimento("Procedimento Desconhecido",true ,150.0, new Especialidade());
        double desconto = consulta.calculateDiscountConvenience(procedimento);
        assertEquals(0.0, desconto);
    }
}
