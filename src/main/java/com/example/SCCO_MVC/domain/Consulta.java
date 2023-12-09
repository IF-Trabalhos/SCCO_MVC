package com.example.SCCO_MVC.domain;
import com.example.SCCO_MVC.exception.RegraNegocioException;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    private Double valorConsulta;
    private Paciente paciente;
    private Dentista dentista;
    private List<Procedimento> procedimentos;
    private Agenda agenda;

    public void validate() {
        if (this.valorConsulta <= 0){
            throw new RegraNegocioException("Valor da consulta invalido");
        }
        if (this.dentista == null) {
            throw new RegraNegocioException("Dentista invalido");
        }
        if (this.paciente == null) {
            throw new RegraNegocioException("Paciente invalido");
        }
        if (this.agenda == null) {
            throw new RegraNegocioException("Agenda invalida");
        }
        for (Procedimento procedimento : this.procedimentos) {
            if (procedimento == null) {
                throw new RegraNegocioException("Procedimento invalido");
            }
            if (procedimento.getEspecialidade() != this.getDentista().getEspecialidade()){
                throw new RegraNegocioException("Dentista com especialidade invalida");
            }
        }
    }

    public void calculateConsultValue() {
        double valorConsulta;
        for (Procedimento procedimento : procedimentos){
            if (this.getPaciente().getConvenio() != null) {
                valorConsulta = procedimento.getValor() * this.calculateDiscountConvenience(procedimento);
            }
            else {
                valorConsulta = procedimento.getValor();
            }
            this.setValorConsulta(valorConsulta + this.valorConsulta);
        }
    }

    public Double calculateDiscountConvenience(Procedimento procedimento) {
        double valorDesconto = 0.0;

        if (Objects.equals(procedimento.getNome(), "Limpeza Dental")) {
            valorDesconto = 0.15;
        } else if (Objects.equals(procedimento.getNome(), "Extração de Dente")) {
            valorDesconto = 0.25;
        } else if (Objects.equals(procedimento.getNome(), "Restauração Dentária")) {
            valorDesconto = 0.20;
        } else if (Objects.equals(procedimento.getNome(), "Tratamento de Canal")) {
            valorDesconto = 0.30;
        } else if (Objects.equals(procedimento.getNome(), "Implante Dentário")) {
            valorDesconto = 0.40;
        } else if (Objects.equals(procedimento.getNome(), "Clareamento Dental")) {
            valorDesconto = 0.10;
        } else if (Objects.equals(procedimento.getNome(), "Ortodontia")) {
            valorDesconto = 0.35;
        } else if (Objects.equals(procedimento.getNome(), "Prostodontia")) {
            valorDesconto = 0.50;
        } else if (Objects.equals(procedimento.getNome(), "Cirurgia Bucomaxilofacial")) {
            valorDesconto = 0.20;
        } else if (Objects.equals(procedimento.getNome(), "Periodontia")) {
            valorDesconto = 0.15;
        }

        return valorDesconto;
    }
}
