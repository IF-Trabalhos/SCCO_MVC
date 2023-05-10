package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.repository.DiaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiaService {

    private DiaRepository repository;

    public DiaService(DiaRepository repository){
        this.repository = repository;
    }

    public List<Dia> getDias(){
        return this.repository.findAll();
    }

    public Optional<Dia> getDiaById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Dia salvar(Dia dia){
        validar(dia);
        return this.repository.save(dia);
    }

    @Transactional
    public void excluir(Dia dia){
        Objects.requireNonNull(dia.getId());
        this.repository.delete(dia);
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
