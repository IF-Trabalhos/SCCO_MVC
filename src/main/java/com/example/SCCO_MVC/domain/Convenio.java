package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Convenio {
    private String nome;
    private String registroAns;
    private String email;
    private String telefone;

    public void validate() {
        if (this.getNome() == null) {
            throw new RegraNegocioException("Nome vazio ou inválido");
        }
        if (this.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome vazio ou inválido");
        }
        if (this.getEmail().length() > 150) {
            throw new RegraNegocioException("Email muito grande");
        }
        if (this.getRegistroAns().length() != 8){
            throw new RegraNegocioException("Registro ANS inválido");
        }
        if (this.getTelefone().length() > 15) {
            throw new RegraNegocioException("Número de telefone muito longo");
        }
    }
}
