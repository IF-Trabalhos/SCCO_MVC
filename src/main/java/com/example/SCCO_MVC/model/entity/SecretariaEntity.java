package com.example.SCCO_MVC.model.entity;


import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class SecretariaEntity extends PessoaEntity {
    private Double salario;
    private String pis;
}
