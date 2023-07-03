package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Dentista;
import com.example.SCCO_MVC.model.entity.Secretaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {
    List<Dentista> findAllByAtivoTrue();

}
