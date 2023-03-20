package com.example.SCCO_MVC.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Paciente paciente;

    @OneToOne
    private Dentista dentista;

    @OneToMany
    private List<Procedimento> procedimentos;

    private double valorConsulta;
    private Date data;
    private Time horaInicio;
    private Time horaFim;
}
