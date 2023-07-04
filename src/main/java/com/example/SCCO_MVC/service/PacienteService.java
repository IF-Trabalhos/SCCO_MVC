package com.example.SCCO_MVC.service;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.model.repository.PacienteRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class PacienteService {
    public PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;

    }

    public List<Paciente> getPacientes() {
        return this.repository.findAll();
    }

    public List<Paciente> getPacientesByAtivoTrue(){return this.repository.findAllByAtivoTrue();}

    public Optional<Paciente> getPacienteById(Long id){
        return this.repository.findById(id);
    }
    public int getNumPacientesAtivos(){return this.repository.countPacientesByAtivoTrue();}
    @Transactional
    public Paciente salvar(Paciente paciente) {
        validar(paciente);
        return this.repository.save(paciente);
    }

    @Transactional
    public void excluir(Paciente paciente) {
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
        if (paciente.getCpf().length() != 14){
            throw new RegraNegocioException("CPF inválido, número de digitos incorreto");
        }
        if (paciente.getDataDeNascimento().getYear() < LocalDate.now().minusMonths(6).getYear()
                || (paciente.getDataDeNascimento().getYear() == LocalDate.now().minusMonths(6).getYear()
                && paciente.getDataDeNascimento().getMonthValue() > LocalDate.now().minusMonths(6).getMonthValue())){
            throw new RegraNegocioException("Data de nascimento inválida, paciente com menos de 6 meses");
        }
        if (paciente.getRg().length() > 10 || paciente.getRg().trim().equals("") ){
            throw new RegraNegocioException("Rg inválido, número de digitos maior que 10");
        }
    }
}