package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Especialidade;
import com.example.SCCO_MVC.dto.EspecialidadeDTO;
import com.example.SCCO_MVC.model.entity.EspecialidadeEntity;
import com.example.SCCO_MVC.model.repository.EspecialidadeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EspecialidadeService {

    private final EspecialidadeRepository repository;

    public EspecialidadeService(EspecialidadeRepository repository){
        this.repository = repository;
    }

    public List<EspecialidadeDTO> get(){
        List<EspecialidadeEntity> especialidades= this.repository.findAll();
        List<EspecialidadeDTO> especialidadesDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( EspecialidadeEntity especialidade: especialidades ) {
            especialidadesDTO.add(EspecialidadeDTO.fromEntityToDTO(especialidade));
        }

        return especialidadesDTO;
    }

    public EspecialidadeDTO get(Long id){
        Optional<EspecialidadeEntity> especialidade = this.repository.findById(id);
        return especialidade.map(EspecialidadeDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public EspecialidadeDTO save(EspecialidadeDTO dto){
        Especialidade especialidade = dto.fromDTOToDomain();
        especialidade.validate();
        EspecialidadeEntity entity = this.repository.save(dto.fromDTOToEntity());
        return EspecialidadeDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(EspecialidadeDTO dto){
        EspecialidadeEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
