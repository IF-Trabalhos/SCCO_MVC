package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Dentista;
import com.example.SCCO_MVC.model.entity.DentistaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistaDTO {
    private Long id;
    private String cro;
    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataDeNascimento;
    private String rg;
    private String email;
    private EnderecoDTO enderecoDTO;
    private EspecialidadeDTO especialidadeDTO;
    private List<ExpedienteDTO> expedientesDTO;

    private static final ModelMapper modelMapper = new ModelMapper();

    public static DentistaDTO fromEntityToDTO(DentistaEntity dentistaEntity) {
        DentistaDTO dto = modelMapper.map(dentistaEntity, DentistaDTO.class);
        dto.setEnderecoDTO(EnderecoDTO.fromEntityToDTO(dentistaEntity.getEnderecoEntity()));
        dto.setEspecialidadeDTO(EspecialidadeDTO.fromEntityToDTO(dentistaEntity.getEspecialidadeEntity()));
        dto.setExpedientesDTO(
                dentistaEntity.getExpedientes()
                        .stream()
                        .map(ExpedienteDTO::fromEntityToDTO)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public Dentista fromDTOToDomain() {
        Dentista dentista = modelMapper.map(this, Dentista.class);
        dentista.setEndereco(this.getEnderecoDTO().fromDTOToDomain());
        dentista.setEspecialidade(this.getEspecialidadeDTO().fromDTOToDomain());
        dentista.setExpedientes(
                this.getExpedientesDTO()
                        .stream()
                        .map(ExpedienteDTO::fromDTOToDomain)
                        .collect(Collectors.toList())
        );
        return dentista;
    }

    public DentistaEntity fromDTOToEntity() {
        DentistaEntity dentistaEntity = modelMapper.map(this, DentistaEntity.class);
        dentistaEntity.setEnderecoEntity(this.getEnderecoDTO().fromDTOToEntity());
        dentistaEntity.setEspecialidadeEntity(this.getEspecialidadeDTO().fromDTOToEntity());
        dentistaEntity.setExpedientes(
                this.getExpedientesDTO()
                        .stream()
                        .map(ExpedienteDTO::fromDTOToEntity)
                        .collect(Collectors.toList())
        );
        return dentistaEntity;
    }
}
