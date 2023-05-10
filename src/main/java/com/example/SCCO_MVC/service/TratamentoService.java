package com.example.SCCO_MVC.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TratamentoService {

    private TratamentoRepository repository;

    public TratamentoService(TratamentoRepository repository){
        this.repository = repository;
    }

    public List<Tratamento> getTratamentos(){
        return this.repository.findAll();
    }

    public Optional<Tratamento> getTratamentoById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Tratamento salvar(Tratamento tratamento){
        return this.repository.save(tratamento);
    }

    @Transactional
    public void excluir(Tratamento tratamento){
        Objects.requireNonNull(tratamento.getId());
        this.repository.delete(tratamento);
    }
}
