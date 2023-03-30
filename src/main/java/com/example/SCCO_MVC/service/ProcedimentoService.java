package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Procedimento;
import com.example.SCCO_MVC.model.repository.ProcedimentoRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProcedimentoService {

    private ProcedimentoRepository repository;

    public ProcedimentoService(ProcedimentoRepository repository){
        this.repository = repository;
    }

    public List<Procedimento> getProcedimentos(){
        return repository.findAll();
    }

    public Optional<Procedimento> getProcedimentoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Procedimento salvar(Procedimento procedimento){
        validar(procedimento);
        return repository.save(procedimento);
    }

    @Transactional
    public void excluir(Procedimento procedimento){
        Objects.requireNonNull(procedimento.getId());
        repository.delete(procedimento);
    }

    public void validar(Procedimento procedimento){
        if (procedimento.getNome() == null || procedimento.getNome().trim().equals("")
                || procedimento.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (procedimento.getEspecialidade() == null || procedimento.getEspecialidade().getId() == null
                || procedimento.getEspecialidade().getId() == 0) {
            throw new RegraNegocioException("Especialidade invalida");
        }
        if (procedimento.getTratamento() == null || procedimento.getTratamento().getId() == null
                || procedimento.getTratamento().getId() == 0) {
            throw new RegraNegocioException("Especialidade invalida");
        }
    }
}
