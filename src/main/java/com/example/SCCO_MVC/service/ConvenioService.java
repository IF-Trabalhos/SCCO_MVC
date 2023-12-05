package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Convenio;
import com.example.SCCO_MVC.dto.ConvenioDTO;
import com.example.SCCO_MVC.model.entity.ConvenioEntity;
import com.example.SCCO_MVC.model.repository.ConvenioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConvenioService {

    private final ConvenioRepository repository;

    public ConvenioService(ConvenioRepository repository){
        this.repository = repository;
    }

    public List<ConvenioDTO> get(){
        List<ConvenioEntity> convenios= this.repository.findAll();
        List<ConvenioDTO> conveniosDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( ConvenioEntity convenio: convenios ) {
            conveniosDTO.add(ConvenioDTO.fromEntityToDTO(convenio));
        }

        return conveniosDTO;
    }

    public ConvenioDTO get(Long id){
        Optional<ConvenioEntity> convenio = this.repository.findById(id);
        return convenio.map(ConvenioDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public ConvenioDTO save(ConvenioDTO dto){
        Convenio convenio = dto.fromDTOToDomain();
        convenio.validate();
        ConvenioEntity entity = this.repository.save(dto.fromDTOToEntity());
        return ConvenioDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(ConvenioDTO dto){
        ConvenioEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
