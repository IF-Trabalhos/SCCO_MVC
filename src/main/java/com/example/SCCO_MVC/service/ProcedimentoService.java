package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Paciente;
import com.example.SCCO_MVC.domain.Procedimento;
import com.example.SCCO_MVC.dto.PacienteDTO;
import com.example.SCCO_MVC.dto.ProcedimentoDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.PacienteEntity;
import com.example.SCCO_MVC.model.entity.ProcedimentoEntity;
import com.example.SCCO_MVC.model.repository.ProcedimentoRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProcedimentoService {

    private final ProcedimentoRepository repository;

    public ProcedimentoService(ProcedimentoRepository repository){
        this.repository = repository;
    }

    public List<ProcedimentoDTO> get(){
        List<ProcedimentoEntity> procedimentos = this.repository.findAll();
        List<ProcedimentoDTO> procedimentosDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( ProcedimentoEntity procedimento: procedimentos ) {
            procedimentosDTO.add(ProcedimentoDTO.fromEntityToDTO(procedimento));
        }

        return procedimentosDTO;
    }

    public ProcedimentoDTO get(Long id){
        Optional<ProcedimentoEntity> procedimento = this.repository.findById(id);
        return procedimento.map(ProcedimentoDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public ProcedimentoDTO save(ProcedimentoDTO dto){
        Procedimento procedimento = dto.fromDTOToDomain();
        procedimento.validate();
        ProcedimentoEntity entity = this.repository.save(dto.fromDTOToEntity());
        return ProcedimentoDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(ProcedimentoDTO dto){
        ProcedimentoEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.repository.delete(entity);
    }
}
