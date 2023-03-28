package com.example.SCCO_MVC.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Dentista extends Pessoa {

    private Integer cro;
    private String agenda;

    @ManyToOne
    private Especialidade especialidade;

}
