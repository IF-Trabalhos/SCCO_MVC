package com.example.SCCO_MVC.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmissao;
    private Double valorTotal;

    @ManyToOne
    private ConsultaEntity consultaEntity;

    @ManyToOne
    private PacienteEntity pacienteEntity;
}
