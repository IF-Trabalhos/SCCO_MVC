package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Agenda;
import com.example.SCCO_MVC.model.repository.AgendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AgendaService {

    private AgendaRepository repository;

    public AgendaService(AgendaRepository repository){
        this.repository = repository;
    }

    public List<Agenda> getAgendas(){
        return this.repository.findAll();
    }

    public Optional<Agenda> getAgendaById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Agenda salvar(Agenda agenda){
        validar(agenda);
        return this.repository.save(agenda);
    }

    @Transactional
    public void excluir(Agenda agenda){
        Objects.requireNonNull(agenda.getId());
        this.repository.delete(agenda);
    }

    public void validar(Agenda agenda){
        if (agenda.getDisponibilidade() == null || agenda.getDisponibilidade().getId() == null
                || agenda.getDisponibilidade().getId() == 0) {
            throw new RegraNegocioException("Disponibilidade invalida");
        }
    }
}
