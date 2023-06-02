package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Expediente;
import com.example.SCCO_MVC.model.repository.ExpedienteRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExpedienteService {

    private ExpedienteRepository repository;

    public ExpedienteService(ExpedienteRepository repository){
        this.repository = repository;
    }

    public List<Expediente> getExpedientes(){
        return this.repository.findAll();
    }

    public Optional<Expediente> getExpedienteById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Expediente salvar(Expediente expediente){
        validar(expediente);
        return this.repository.save(expediente);
    }

    @Transactional
    public void excluir(Expediente expediente){
        Objects.requireNonNull(expediente.getId());
        this.repository.delete(expediente);
    }

    public void validar(Expediente expediente){
        if (expediente.getHoraInicial() == null) {
            throw new RegraNegocioException("Hora inicial invalida");
        }
        if (expediente.getHoraFinal() == null) {
            throw new RegraNegocioException("Hora final invalida");
        }
    }
}
