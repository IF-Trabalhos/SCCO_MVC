package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Secretaria;
import com.example.SCCO_MVC.model.entity.SecretariaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretariaDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataDeNascimento;
    private String rg;
    private String email;
    private Double salario;
    private String pis;
    private EnderecoDTO enderecoDTO;
    private boolean ativo;

    public static SecretariaDTO fromEntityToDTO(SecretariaEntity secretatia){
        ModelMapper modelMapper = new ModelMapper();
        SecretariaDTO dto = modelMapper.map(secretatia, SecretariaDTO.class);
        dto.setEnderecoDTO(EnderecoDTO.fromEntityToDTO(secretatia.getEnderecoEntity()));
        return dto;
    }

    public Secretaria fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();
        Secretaria secretaria = modelMapper.map(this, Secretaria.class);
        secretaria.setEndereco(this.getEnderecoDTO().fromDTOToDomain());
        return secretaria;
    }

    public SecretariaEntity fromDTOToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        SecretariaEntity secretariaEntity = modelMapper.map(this, SecretariaEntity.class);
        secretariaEntity.setEnderecoEntity(this.getEnderecoDTO().fromDTOToEntity());
        return secretariaEntity;
    }
}
