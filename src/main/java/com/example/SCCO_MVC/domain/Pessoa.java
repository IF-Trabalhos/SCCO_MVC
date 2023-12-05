package com.example.SCCO_MVC.domain;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataDeNascimento;
    private String rg;
    private String email;
    private boolean ativo;
    private Endereco endereco;
}
