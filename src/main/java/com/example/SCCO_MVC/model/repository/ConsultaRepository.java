package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    @Query(value = "SELECT a FROM Consulta a WHERE a.dentista.id = :id")
    Optional<Consulta> getConsultasByDentistaId(Long id);
    @Query(value = "SELECT ad FROM Consulta ad WHERE ad.data = :data")
    Optional<Consulta> getConsultasByData(Date data);
}
