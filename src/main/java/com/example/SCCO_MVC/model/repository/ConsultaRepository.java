package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {

}
