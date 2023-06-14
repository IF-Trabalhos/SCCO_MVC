package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    @Query(value = "SELECT c FROM Consulta c WHERE c.data BETWEEN :dataInicial AND :dataFinal")
    List<Consulta> getConsultasByIntervaloData(@Param("dataInicial") LocalDate dataInicial,
                                                @Param("dataFinal") LocalDate dataFinal);
}
