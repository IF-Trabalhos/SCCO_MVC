package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Conta;
import com.example.SCCO_MVC.model.repository.ContaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContaService {

    private ContaRepository repository;

    public ContaService(ContaRepository repository){
        this.repository = repository;
    }

    public List<Conta> getContas(){
        return this.repository.findAll();
    }

    public Optional<Conta> getContaById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Conta salvar(Conta conta){
        validar(conta);
        return this.repository.save(conta);
    }

    @Transactional
    public void excluir(Conta conta){
        Objects.requireNonNull(conta.getId());
        this.repository.delete(conta);
    }

    public void validar(Conta conta){
        if (conta.getConsulta() == null || conta.getConsulta().getId() == null
                || conta.getConsulta().getId() == 0) {
            throw new RegraNegocioException("Consulta invalida");
        }
        if (conta.getPaciente() == null || conta.getPaciente().getId() == null
                || conta.getPaciente().getId() == 0) {
            throw new RegraNegocioException("Paciente invalido");
        }
    }
}
