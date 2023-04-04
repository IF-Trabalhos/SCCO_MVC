package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.repository.DentistaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DentistaService {

    private DentistaRepository repository;

    public DentistaService(DentistaRepository repository){
        this.repository = repository;
    }

    public List<Dentista> getDentistas(){
        return repository.findAll();
    }

    public Optional<Dentista> getDentistaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Dentista salvar(Dentista dentista){
        validar(dentista);
        return repository.save(dentista);
    }

    @Transactional
    public void excluir(Dentista dentista){
        Objects.requireNonNull(dentista.getId());
        repository.delete(dentista);
    }

    public void validar(Dentista dentista){
        if (dentista.getNome() == null || dentista.getNome().trim().equals("")
                || dentista.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
    }
}
