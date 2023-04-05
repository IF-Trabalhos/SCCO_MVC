package com.example.SCCO_MVC.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Boolean status;
    private Double valor;

    @ManyToOne
    private Especialidade especialidade;
    @ManyToOne
    private Tratamento tratamento;
}
