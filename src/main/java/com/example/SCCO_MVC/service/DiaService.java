package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Dia;
import com.example.SCCO_MVC.model.repository.DiaRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DiaService {

    private DiaRepository repository;

    public DiaService(DiaRepository repository){
        this.repository = repository;
    }

    public List<Dia> getDias(){
        return repository.findAll();
    }

    public Optional<Dia> getDiaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Dia salvar(Dia dia){
        validar(dia);
        return repository.save(dia);
    }

    @Transactional
    public void excluir(Dia dia){
        Objects.requireNonNull(dia.getId());
        repository.delete(dia);
    }

    public void validar(Dia dia){
        if (dia.getNome() == null || dia.getNome().trim().equals("")
                || dia.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (dia.getHoraInicial() == null) {
            throw new RegraNegocioException("Hora de inicio invalida");
        }
        if (dia.getHoraFinal() == null) {
            throw new RegraNegocioException("Hora de término invalida");
        }
    }
}
