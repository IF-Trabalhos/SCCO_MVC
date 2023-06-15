package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpedienteRepository extends JpaRepository<Expediente,Long> {
}
