package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Disponibilidade;
import com.example.SCCO_MVC.model.repository.DisponibilidadeRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DisponibilidadeService {

    private DisponibilidadeRepository repository;

    public DisponibilidadeService(DisponibilidadeRepository repository){
        this.repository = repository;
    }

    public List<Disponibilidade> getDisponibilidades(){
        return repository.findAll();
    }

    public Optional<Disponibilidade> getDisponibilidadeById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Disponibilidade salvar(Disponibilidade disponibilidade){
        validar(disponibilidade);
        return repository.save(disponibilidade);
    }

    @Transactional
    public void excluir(Disponibilidade disponibilidade){
        Objects.requireNonNull(disponibilidade.getId());
        repository.delete(disponibilidade);
    }

    public void validar(Disponibilidade disponibilidade){
        if (disponibilidade.getHoraInicialIntervalo() == null) {
            throw new RegraNegocioException("Hora de inicio do intervalo invalida");
        }
        if (disponibilidade.getHoraFinalIntervalo() == null) {
            throw new RegraNegocioException("Hora de término do intervalo invalida");
        }
    }
}