package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Secretaria;
import com.example.SCCO_MVC.dto.SecretariaDTO;
import com.example.SCCO_MVC.model.entity.SecretariaEntity;
import com.example.SCCO_MVC.model.repository.SecretariaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecretariaService {

    private final SecretariaRepository repository;

    public SecretariaService(SecretariaRepository repository){
        this.repository = repository;
    }

    public List<SecretariaDTO> get(){
        List<SecretariaEntity> secretarias = this.repository.findAll();
        List<SecretariaDTO> secretariasDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( SecretariaEntity secretaria: secretarias ) {
            secretariasDTO.add(SecretariaDTO.fromEntityToDTO(secretaria));
        }
        return secretariasDTO;
    }

    public SecretariaDTO get(Long id){
        Optional<SecretariaEntity> secretaria = this.repository.findById(id);
        return secretaria.map(SecretariaDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public SecretariaDTO save(SecretariaDTO dto){
        Secretaria secretaria = dto.fromDTOToDomain();
        secretaria.validate();
        SecretariaEntity entity = this.repository.save(dto.fromDTOToEntity());
        return SecretariaDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(SecretariaDTO dto){
        SecretariaEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}