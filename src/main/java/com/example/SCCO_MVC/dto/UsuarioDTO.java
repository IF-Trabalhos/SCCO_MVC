package com.example.SCCO_MVC.dto;

import com.example.SCCO_MVC.domain.Usuario;
import com.example.SCCO_MVC.model.entity.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String login;
    private String senha;

    public static UsuarioDTO fromEntityToDTO(UsuarioEntity usuarioEntity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(usuarioEntity, UsuarioDTO.class);
    }

    public Usuario fromDTOToDomain() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Usuario.class);
    }

    public UsuarioEntity fromDTOToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, UsuarioEntity.class);
    }
}
