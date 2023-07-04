package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SecretariaRepository extends JpaRepository<Secretaria, Long> {
    List<Secretaria> findAllByAtivoTrue();
}
