package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Paciente;
import com.example.SCCO_MVC.model.entity.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository  extends JpaRepository<Paciente, Long> {
    List<Paciente> findAllByAtivoTrue();

    int countPacientesByAtivoTrue();

}
