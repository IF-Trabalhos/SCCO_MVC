package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Consulta;
import com.example.SCCO_MVC.model.repository.ConsultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConsultaService {

    private ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository){
        this.repository = repository;
    }

    public List<Consulta> getConsultas(){
        return this.repository.findAll();
    }

    public Optional<Consulta> getConsultaById(Long id){
        return this.repository.findById(id);
    }

    @Transactional
    public Consulta salvar(Consulta consulta){
        validar(consulta);
        return this.repository.save(consulta);
    }

    @Transactional
    public void excluir(Consulta consulta){
        Objects.requireNonNull(consulta.getId());
        this.repository.delete(consulta);
    }

    public void validar(Consulta consulta){
        if (consulta.getData() == null) {
            throw new RegraNegocioException("Data invalida");
        }
        if (consulta.getHoraInicio() == null) {
            throw new RegraNegocioException("Hora de inicio invalida");
        }
        if (consulta.getHoraFim() == null) {
            throw new RegraNegocioException("Hora de término invalida");
        }
        if (consulta.getDentista() == null || consulta.getDentista().getId() == null
                || consulta.getDentista().getId() == 0) {
            throw new RegraNegocioException("Especialidade invalida");
        }
        if (consulta.getPaciente() == null || consulta.getPaciente().getId() == null
                || consulta.getPaciente().getId() == 0) {
            throw new RegraNegocioException("Especialidade invalida");
        }
    }
}
