package com.example.SCCO_MVC.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataDeNascimento;
    private String rg;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;
}
