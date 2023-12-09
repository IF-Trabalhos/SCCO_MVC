package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.dto.ContaDTO;
import com.example.SCCO_MVC.domain.Conta;
import com.example.SCCO_MVC.model.entity.ContaEntity;
import com.example.SCCO_MVC.model.repository.ContaRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContaService {

    private final ContaRepository repository;

    public ContaService(ContaRepository repository){
        this.repository = repository;
    }

    public List<ContaDTO> get(){
        List<ContaEntity> contas = this.repository.findAll();
        List<ContaDTO> contasDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( ContaEntity conta: contas ) {
            contasDTO.add(ContaDTO.fromEntityToDTO(conta));
        }

        return contasDTO;
    }

    public ContaDTO get(Long id){
        Optional<ContaEntity> conta = this.repository.findById(id);
        return conta.map(ContaDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public ContaDTO save(ContaDTO dto){
        Conta conta = dto.fromDTOToDomain();
        conta.validate();
        ContaEntity entity = this.repository.save(dto.fromDTOToEntity());
        return ContaDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(ContaDTO dto){
        ContaEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
