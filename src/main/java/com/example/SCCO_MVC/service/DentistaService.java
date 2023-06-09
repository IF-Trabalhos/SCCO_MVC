package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.repository.DentistaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<Dentista> getDentistaByAtivoTrue(){return this.repository.findAllByAtivoTrue();}
    public int getNumDentistasAtivos(){return this.repository.countDentistasByAtivoTrue();}
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
        if (dentista.getCro().length() != 7){
            throw new RegraNegocioException("Cro inválido, diferente de 7 digitos");
        }
        if (dentista.getCpf().length() != 14){
            throw new RegraNegocioException("CPF inválido, número de digitos incorreto");
        }
        if (dentista.getDataDeNascimento().getYear() > LocalDate.now().minusYears(18).getYear()){
            throw new RegraNegocioException("Data de nascimento inválida, dentista menor de idade");
        }
    }
}
