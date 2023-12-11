package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Convenio;
import com.example.SCCO_MVC.domain.Paciente;
import com.example.SCCO_MVC.model.entity.PacienteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataDeNascimento;
    private String rg;
    private String email;
    private String numProntuario;
    private EnderecoDTO enderecoDTO;
    private Long convenioId;

    public static PacienteDTO fromEntityToDTO(PacienteEntity pacienteEntity){
        ModelMapper modelMapper = new ModelMapper();
        PacienteDTO dto = modelMapper.map(pacienteEntity, PacienteDTO.class);
        dto.setEnderecoDTO(EnderecoDTO.fromEntityToDTO(pacienteEntity.getEnderecoEntity()));
        return dto;
    }

    public Paciente fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();
        Paciente paciente = modelMapper.map(this, Paciente.class);
        paciente.setEndereco(this.getEnderecoDTO().fromDTOToDomain());
        return paciente;
    }

    public PacienteEntity fromDTOToEntity(){
        ModelMapper modelMapper = new ModelMapper();
        PacienteEntity pacienteEntity = modelMapper.map(this, PacienteEntity.class);

        pacienteEntity.setEnderecoEntity(this.getEnderecoDTO().fromDTOToEntity());

        return pacienteEntity;
    }
}
