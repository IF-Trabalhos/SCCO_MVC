package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.EnderecoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;
    private String numero;
    private String complemento;
    private String cep;

    public void validate(){
        if (this.getLogradouro() == null || this.getLogradouro().trim().equals("")
                || this.getLogradouro().length() > 255) {
            throw new RegraNegocioException("Logradouro vazio ou invalido");
        }
        if (this.getBairro() == null || this.getBairro().trim().equals("")
                || this.getBairro().length() > 255) {
            throw new RegraNegocioException("Bairro invalido ou vazio");
        }
        if (this.getCep() == null || this.getCep().trim().equals("")
                || this.getCep().length() != 9) {
            throw new RegraNegocioException("Cep invalido ou vazio");
        }
        if (this.getNumero().length() > 10 || this.getNumero().length() < 1){
            throw new RegraNegocioException("Número inválido");
        }
        if (this.getCidade().equals("") || this.getCidade().length() > 255){
            throw new RegraNegocioException("Cidade inválida, campo vazio ou com digitos em excesso");
        }
        if (this.getUf().length() != 2){
            throw new RegraNegocioException("UF inválida");
        }
    }
}
