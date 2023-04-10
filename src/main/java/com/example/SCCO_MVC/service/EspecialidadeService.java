package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Especialidade;
import com.example.SCCO_MVC.model.repository.EspecialidadeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EspecialidadeService {

    private EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository){
        this.repository = repository;
    }

    public List<Especialidade> getEspecialidades(){
        return this.repository.findAll();
    }

    public Optional<Especialidade> getEspecialidadeById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Especialidade salvar(Especialidade especialidade){
        validar(especialidade);
        return this.repository.save(especialidade);
    }

    @Transactional
    public void excluir(Especialidade especialidade){
        Objects.requireNonNull(especialidade.getId());
        this.repository.delete(especialidade);
    }

    public void validar(Especialidade especialidade){
        if (especialidade.getNome() == null || especialidade.getNome().trim().equals("")
                || especialidade.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
    }
}
