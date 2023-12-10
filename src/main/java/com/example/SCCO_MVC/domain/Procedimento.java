package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Procedimento {
    private String nome;
    private Boolean status;
    private Double valor;

    private Especialidade especialidade;

    public void validate(){
        if (this.getNome() == null || this.getNome().trim().equals("")
                || this.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (this.valor <= 0) {
            throw new RegraNegocioException("Valor invalido");
        }
        if (this.getEspecialidade() == null) {
            throw new RegraNegocioException("Especialidade invalida");
        }
        if (this.getStatus() == null){
            throw new RegraNegocioException("Campo status não selecionado");
        }
    }
}
