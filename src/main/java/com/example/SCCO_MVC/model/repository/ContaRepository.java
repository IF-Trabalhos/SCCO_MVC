package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<ContaEntity, Long> {
}
