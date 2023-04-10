package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Secretaria;
import com.example.SCCO_MVC.model.repository.SecretariaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SecretariaService {

    private SecretariaRepository repository;

    public SecretariaService(SecretariaRepository repository){
        this.repository = repository;
    }

    public List<Secretaria> getSecretarias(){
        return repository.findAll();
    }

    public Optional<Secretaria> getSecretariaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Secretaria salvar(Secretaria secretaria){
        validar(secretaria);
        return repository.save(secretaria);
    }

    @Transactional
    public void excluir(Secretaria secretaria){
        Objects.requireNonNull(secretaria.getId());
        repository.delete(secretaria);
    }

    public void validar(Secretaria secretaria){
        if (secretaria.getPis() == null || secretaria.getPis().trim().equals("")
                || secretaria.getPis().length() > 11) {
            throw new RegraNegocioException("Numero de Pis vazio ou invalido");
        }
        if (secretaria.getNome() == null || secretaria.getNome().trim().equals("")
                || secretaria.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
    }
}