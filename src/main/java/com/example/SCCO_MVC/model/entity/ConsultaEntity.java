package com.example.SCCO_MVC.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List; // Importe a classe List

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorConsulta;

    @ManyToOne
    private PacienteEntity pacienteEntity;

    @ManyToOne
    private DentistaEntity dentistaEntity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private List<ProcedimentoEntity> procedimentos;

    @ManyToOne
    private AgendaEntity agendaEntity;
}
