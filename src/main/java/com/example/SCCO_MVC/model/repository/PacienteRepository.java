package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository  extends JpaRepository<PacienteEntity, Long> {
    List<PacienteEntity> findAllByAtivoTrue();

}
