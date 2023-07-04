package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.model.repository.EnderecoRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    private EnderecoRepository repository;

    public EnderecoService(EnderecoRepository repository){
        this.repository = repository;
    }
    @Transactional
    public Endereco salvar(Endereco endereco){
        validar(endereco);
        return this.repository.save(endereco);
    }
    public void validar(Endereco endereco){
        if (endereco.getLogradouro() == null || endereco.getLogradouro().trim().equals("")
                || endereco.getLogradouro().length() > 255) {
            throw new RegraNegocioException("Logradouro vazio ou invalido");
        }
        if (endereco.getBairro() == null || endereco.getBairro().trim().equals("")
                || endereco.getBairro().length() > 255) {
            throw new RegraNegocioException("Bairro invalido ou vazio");
        }
        if (endereco.getCep() == null || endereco.getCep().trim().equals("")
                || endereco.getCep().length() != 9) {
            throw new RegraNegocioException("Cep invalido ou vazio");
        }
        if (endereco.getNumero().length() > 10 || endereco.getNumero().length() < 1){
            throw new RegraNegocioException("Número inválido");
        }
        if (endereco.getCidade().equals("") || endereco.getCidade().length() > 255){
            throw new RegraNegocioException("Cidade inválida, campo vazio ou com digitos em excesso");
        }
        if (endereco.getUf().length() != 2){
            throw new RegraNegocioException("UF inválida");
        }
    }
}
