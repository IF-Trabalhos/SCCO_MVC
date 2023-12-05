package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.ProcedimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<ProcedimentoEntity, Long> {
}
