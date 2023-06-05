package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Secretaria;
import com.example.SCCO_MVC.model.repository.SecretariaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecretariaService {

    private SecretariaRepository repository;

    public SecretariaService(SecretariaRepository repository){
        this.repository = repository;
    }

    public List<Secretaria> getSecretarias(){
        return this.repository.findAll();
    }

    public Optional<Secretaria> getSecretariaById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Secretaria salvar(Secretaria secretaria){
        validar(secretaria);
        return this.repository.save(secretaria);
    }

    @Transactional
    public void excluir(Secretaria secretaria){
        Objects.requireNonNull(secretaria.getId());
        this.repository.delete(secretaria);
    }

    public void validar(Secretaria secretaria){
        if (secretaria.getPis() == null || secretaria.getPis().trim().equals("")
                || secretaria.getPis().length() > 11) {
            throw new RegraNegocioException("Numero de Pis vazio ou invalido");
        }
        if (secretaria.getNome() == null || secretaria.getNome().trim().equals("")
                || secretaria.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
    }
}