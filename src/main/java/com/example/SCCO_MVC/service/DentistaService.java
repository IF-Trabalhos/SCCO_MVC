package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Dentista;
import com.example.SCCO_MVC.dto.DentistaDTO;
import com.example.SCCO_MVC.model.entity.DentistaEntity;
import com.example.SCCO_MVC.model.repository.DentistaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DentistaService {

    private DentistaRepository repository;

    public DentistaService(DentistaRepository repository){
        this.repository = repository;
    }

    public List<DentistaDTO> getAtivos(){
        List<DentistaEntity> dentistas = this.repository.findAllByAtivoTrue();
        List<DentistaDTO> dentistasDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( DentistaEntity dentista: dentistas ) {
            dentistasDTO.add(DentistaDTO.fromEntityToDTO(dentista));
        }
        return dentistasDTO;
    }

    public List<DentistaDTO> get(){
        List<DentistaEntity> dentistas= this.repository.findAll();
        List<DentistaDTO> dentistasDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( DentistaEntity dentista: dentistas ) {
            dentistasDTO.add(DentistaDTO.fromEntityToDTO(dentista));
        }

        return dentistasDTO;
    }

    public DentistaDTO get(Long id){
        Optional<DentistaEntity> dentista = this.repository.findById(id);
        return dentista.map(DentistaDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public DentistaDTO save(DentistaDTO dto){
        Dentista dentista = dto.fromDTOToDomain();
        dentista.validate();
        DentistaEntity entity = this.repository.save(dto.fromDTOToEntity());
        return DentistaDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(DentistaDTO dto){
        DentistaEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
