package com.example.SCCO_MVC.model.repository;

import com.example.SCCO_MVC.model.entity.SecretariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecretariaRepository extends JpaRepository<SecretariaEntity, Long> {
    List<SecretariaEntity> findAllByAtivoTrue();
}
