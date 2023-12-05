package com.example.SCCO_MVC.domain;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.ConvenioEntity;
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
    private Double desconto;

    public void validate(){
        if (this.getNome() == null || this.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (this.getEmail().length() > 150){
            throw new RegraNegocioException("Email grande de mais");
        }
        if (this.getRegistroAns().length() != 8){
            throw new RegraNegocioException("Registro ANS inválido");
        }
    }
}
