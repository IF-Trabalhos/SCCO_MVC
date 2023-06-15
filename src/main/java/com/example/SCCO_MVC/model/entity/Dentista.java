package com.example.SCCO_MVC.model.entity;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Dentista extends Pessoa {

    private String cro;

    @ManyToOne
    private Especialidade especialidade;

    @ManyToOne
    private Expediente expediente;
}
