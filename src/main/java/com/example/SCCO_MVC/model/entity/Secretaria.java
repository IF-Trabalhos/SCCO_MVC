package com.example.SCCO_MVC.model.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Secretaria extends Pessoa{
    private double salario;
    private String pis;
}
