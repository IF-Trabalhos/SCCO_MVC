package com.example.SCCO_MVC.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorConsulta;
    private LocalDate data;
    private Time horaInicial;
    private Time horaFinal;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Dentista dentista;

    @ManyToOne
    private Procedimento procedimento;
}
