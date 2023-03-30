package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Agenda;
import com.example.SCCO_MVC.model.repository.AgendaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AgendaService {

    private AgendaRepository repository;

    public AgendaService(AgendaRepository repository){
        this.repository = repository;
    }

    public List<Agenda> getAgendas(){
        return repository.findAll();
    }

    public Optional<Agenda> getAgendaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Agenda salvar(Agenda agenda){
        validar(agenda);
        return repository.save(agenda);
    }

    @Transactional
    public void excluir(Agenda agenda){
        Objects.requireNonNull(agenda.getId());
        repository.delete(agenda);
    }

    public void validar(Agenda agenda){
        if (agenda.getDisponibilidade() == null || agenda.getDisponibilidade().getId() == null
                || agenda.getDisponibilidade().getId() == 0) {
            throw new RegraNegocioException("Especialidade invalida");
        }
    }
}
