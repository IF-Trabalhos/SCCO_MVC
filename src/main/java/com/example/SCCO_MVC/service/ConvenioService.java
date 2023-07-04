package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Convenio;
import com.example.SCCO_MVC.model.repository.ConvenioRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConvenioService {

    private ConvenioRepository repository;

    public ConvenioService(ConvenioRepository repository){
        this.repository = repository;
    }

    public List<Convenio> getConvenios(){
        return this.repository.findAll();
    }

    public Optional<Convenio> getConvenioById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Convenio salvar(Convenio convenio){
        validar(convenio);
        return this.repository.save(convenio);
    }

    @Transactional
    public void excluir(Convenio convenio){
        Objects.requireNonNull(convenio.getId());
        this.repository.delete(convenio);
    }

    public void validar(Convenio convenio){
        if (convenio.getNome() == null || convenio.getNome().trim().equals("")
                || convenio.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (convenio.getEmail().length() > 150){
            throw new RegraNegocioException("Email grande de mais");
        }
        if (convenio.getRegistroAns().length() != 8){
            throw new RegraNegocioException("Registro ANS inválido");
        }
    }
}
