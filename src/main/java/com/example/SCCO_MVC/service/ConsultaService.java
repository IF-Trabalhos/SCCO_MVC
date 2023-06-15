package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Consulta;
import com.example.SCCO_MVC.model.repository.ConsultaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public Optional<Consulta> getConsultaByDentistaId(Long id){
        return this.repository.getConsultasByDentistaId(id);
    }
    public Optional<Consulta> getConsultaByData(Date data){
        return this.repository.getConsultasByData(data);
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
        if (consulta.getHoraInicial() == null) {
            throw new RegraNegocioException("Hora inicial invalida");
        }
        if (consulta.getHoraFinal() == null) {
            throw new RegraNegocioException("Hora final invalida");
        }
        if (consulta.getDentista() == null || consulta.getDentista().getId() == null
                || consulta.getDentista().getId() == 0) {
            throw new RegraNegocioException("Dentista invalido");
        }
        if (consulta.getPaciente() == null || consulta.getPaciente().getId() == null
                || consulta.getPaciente().getId() == 0) {
            throw new RegraNegocioException("Paciente invalido");
        }
        if (consulta.getProcedimento() == null || consulta.getProcedimento().getId() == null
                || consulta.getProcedimento().getId() == 0) {
            throw new RegraNegocioException("Procedimento invalido");
        }
    }
}
