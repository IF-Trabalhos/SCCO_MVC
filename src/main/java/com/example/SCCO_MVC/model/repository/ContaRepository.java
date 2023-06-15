package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
}
