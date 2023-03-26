package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository  extends JpaRepository<Paciente, Long> {
}
