package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.EspecialidadeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especialidade {
    private String nome;
    private Boolean status;

    public void validate(){
        if (this.getNome() == null || this.getNome().trim().equals("")
                || this.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (this.getStatus() == null){
            throw new RegraNegocioException("Status da especialidade não selecionado");
        }
    }
}
