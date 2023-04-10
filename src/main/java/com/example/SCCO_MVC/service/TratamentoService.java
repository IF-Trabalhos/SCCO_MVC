package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.model.entity.Tratamento;
import com.example.SCCO_MVC.model.repository.TratamentoRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TratamentoService {

    private TratamentoRepository repository;

    public TratamentoService(TratamentoRepository repository){
        this.repository = repository;
    }

    public List<Tratamento> getTratamentos(){
        return repository.findAll();
    }

    public Optional<Tratamento> getTratamentoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Tratamento salvar(Tratamento tratamento){
        return repository.save(tratamento);
    }

    @Transactional
    public void excluir(Tratamento tratamento){
        Objects.requireNonNull(tratamento.getId());
        repository.delete(tratamento);
    }
}
