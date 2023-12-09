package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.DentistaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DentistaRepository extends JpaRepository<DentistaEntity, Long> {
    List<DentistaEntity> findAllByAtivoTrue();

    int countDentistasByAtivoTrue();

}
