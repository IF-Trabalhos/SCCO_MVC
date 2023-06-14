package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.repository.DentistaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DentistaService {

    private DentistaRepository repository;

    public DentistaService(DentistaRepository repository){
        this.repository = repository;
    }

    public List<Dentista> getDentistas(){
        return this.repository.findAll();
    }

    public Optional<Dentista> getDentistaById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Dentista salvar(Dentista dentista){
        validar(dentista);
        return this.repository.save(dentista);
    }

    @Transactional
    public void excluir(Dentista dentista){
        Objects.requireNonNull(dentista.getId());
        this.repository.delete(dentista);
    }

    public void validar(Dentista dentista){
        if (dentista.getNome() == null || dentista.getNome().trim().equals("")
                || dentista.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (dentista.getEspecialidade() == null || dentista.getEspecialidade().getId() == null
                || dentista.getEspecialidade().getId() == 0) {
            throw new RegraNegocioException("Especialidade invalida");
        }
    }
}
