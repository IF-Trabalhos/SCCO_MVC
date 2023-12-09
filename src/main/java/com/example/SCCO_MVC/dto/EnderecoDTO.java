package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Endereco;
import com.example.SCCO_MVC.model.entity.EnderecoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private Long id;

    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;
    private String numero;
    private String complemento;
    private String cep;

    public static EnderecoDTO fromEntityToDTO(EnderecoEntity enderecoEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(enderecoEntity, EnderecoDTO.class);
    }

    public Endereco fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Endereco.class);
    }

    public EnderecoEntity fromDTOToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, EnderecoEntity.class);
    }
}
