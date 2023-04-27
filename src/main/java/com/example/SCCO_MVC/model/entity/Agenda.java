package com.example.SCCO_MVC.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double faturamento;

    @OneToOne
    private Disponibilidade disponibilidade;

    @OneToOne
    private Dentista dentista;

}
