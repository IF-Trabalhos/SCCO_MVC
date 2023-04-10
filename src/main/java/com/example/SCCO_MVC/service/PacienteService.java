package com.example.SCCO_MVC.service;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Endereco;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.model.repository.EnderecoRepository;
import com.example.SCCO_MVC.model.repository.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class PacienteService {
    private PacienteRepository repository;

    private PacienteService(PacienteRepository repository) {
        this.repository = repository;

    }

    private List<Paciente> getPacientes() {
        return this.repository.findAll();
    }

    @Transactional
    public Paciente salvar(Paciente paciente) {
        validar(paciente);
        return repository.save(paciente);
    }

    @Transactional
    private void excluir(Paciente paciente) {
        Objects.requireNonNull(paciente.getId());
        this.repository.delete(paciente);
    }

    public void validar(Paciente paciente) {
        if (paciente.getNumProntuario() == null || paciente.getNumProntuario().trim().equals("")
                || paciente.getNumProntuario().length() > 14) {
            throw new RegraNegocioException("Numero de Prontuario vazio ou invalido");
        }
        if (paciente.getNome() == null || paciente.getNome().trim().equals("")
                || paciente.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
    }
}