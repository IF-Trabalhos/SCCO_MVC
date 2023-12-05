package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.SecretariaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Secretaria extends Pessoa {
    private Double salario;
    private String pis;

    public void validate(){
        if (this.getPis() == null || this.getPis().trim().equals("")
                || this.getPis().length() > 11) {
            throw new RegraNegocioException("Numero de Pis vazio ou invalido");
        }
        if (this.getNome() == null || this.getNome().trim().equals("")
                || this.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (this.getCpf().length() != 14){
            throw new RegraNegocioException("CPF inválido, número de digitos incorreto");
        }
        if (this.getDataDeNascimento().getYear() > LocalDate.now().minusYears(18).getYear()){
            throw new RegraNegocioException("Data de nascimento inválida, secretaria menor de idade");
        }
    }
}
