package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Procedimento;
import com.example.SCCO_MVC.dto.*;
import com.example.SCCO_MVC.domain.Consulta;
import com.example.SCCO_MVC.model.entity.*;
import com.example.SCCO_MVC.model.repository.*;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final DentistaRepository dentistaRepository;
    private final ProcedimentoRepository procedimentoRepository;
    private final AgendaRepository agendaRepository;

    public List<ConsultaDTO> get(){
        List<ConsultaEntity> consultas = this.consultaRepository.findAll();
        List<ConsultaDTO> consultasDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( ConsultaEntity consulta: consultas ) {
            consultasDTO.add(ConsultaDTO.fromEntityToDTO(consulta));
        }

        return consultasDTO;
    }

    public ConsultaDTO get(Long id){
        Optional<ConsultaEntity> consulta = this.consultaRepository.findById(id);
        return consulta.map(ConsultaDTO::fromEntityToDTO).orElse(null);
    }

    @Transactional
    public ConsultaDTO save(ConsultaDTO dto){
        PacienteEntity pacienteEntity = this.pacienteRepository.getById(dto.getPacienteId());
        DentistaEntity dentistaEntity = this.dentistaRepository.getById(dto.getDentistaId());
        AgendaEntity agendaEntity = this.agendaRepository.getById(dto.getAgendaId());
        List<ProcedimentoEntity> procedimentosEntity = new ArrayList<>();
        List<Procedimento> procedimentos = new ArrayList<>();

        for (Long procedimentoId: dto.getProcedimentosId()) {
            ProcedimentoEntity procedimentoEntity = this.procedimentoRepository.getById(procedimentoId);
            ProcedimentoDTO procedimentoDTO = ProcedimentoDTO.fromEntityToDTO(procedimentoEntity);
            procedimentosEntity.add(procedimentoEntity);
            procedimentos.add(procedimentoDTO.fromDTOToDomain());
        }

        PacienteDTO pacienteDTO = PacienteDTO.fromEntityToDTO(pacienteEntity);
        DentistaDTO dentistaDTO = DentistaDTO.fromEntityToDTO(dentistaEntity);
        AgendaDTO agendaDTO = AgendaDTO.fromEntityToDTO(agendaEntity);

        Consulta consulta = dto.fromDTOToDomain();
        consulta.setPaciente(pacienteDTO.fromDTOToDomain());
        consulta.setDentista(dentistaDTO.fromDTOToDomain());
        consulta.setAgenda(agendaDTO.fromDTOToDomain());
        consulta.setProcedimentos(procedimentos);
        consulta.validate();

        ConsultaEntity consultaEntity = dto.fromDTOToEntity();
        consultaEntity.setPacienteEntity(pacienteEntity);
        consultaEntity.setDentistaEntity(dentistaEntity);
        consultaEntity.setAgendaEntity(agendaEntity);
        consultaEntity.setProcedimentos(procedimentosEntity);

        ConsultaEntity entity = this.consultaRepository.save(consultaEntity);
        return ConsultaDTO.fromEntityToDTO(entity);
    }

    @Transactional
    public void delete(ConsultaDTO dto){
        ConsultaEntity entity = dto.fromDTOToEntity();
        Objects.requireNonNull(entity.getId());
        this.consultaRepository.delete(entity);
    }
}
