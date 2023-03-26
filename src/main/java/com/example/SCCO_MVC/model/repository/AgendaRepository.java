package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda,Long> {
}
