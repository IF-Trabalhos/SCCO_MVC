package com.example.SCCO_MVC.service;

import com.example.SCCO_MVC.domain.Secretaria;
import com.example.SCCO_MVC.domain.Usuario;
import com.example.SCCO_MVC.dto.SecretariaDTO;
import com.example.SCCO_MVC.dto.UsuarioDTO;
import com.example.SCCO_MVC.exception.SenhaInvalidaException;
import com.example.SCCO_MVC.model.entity.UsuarioEntity;
import com.example.SCCO_MVC.model.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioDTO> get(){
        List<UsuarioEntity> usuarios= this.repository.findAll();
        List<UsuarioDTO> usuariosDTO = new java.util.ArrayList<>(Collections.emptyList());
        for ( UsuarioEntity usuario: usuarios ) {
            usuariosDTO.add(UsuarioDTO.fromEntityToDTO(usuario));
        }

        return usuariosDTO;
    }

    @Transactional
    public UsuarioDTO save(UsuarioDTO dto){
        Usuario usuario = dto.fromDTOToDomain();
        UsuarioEntity entity = this.repository.save(dto.fromDTOToEntity());
        return UsuarioDTO.fromEntityToDTO(entity);
    }

    public UserDetails autenticar(UsuarioEntity usuarioEntity, PasswordEncoder encoder){
        UserDetails user = loadUserByUsername(usuarioEntity.getLogin());
        boolean senhasBatem = encoder.matches(usuarioEntity.getSenha(), user.getPassword());

        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioEntity usuarioEntity = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = usuarioEntity.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(usuarioEntity.getLogin())
                .password(usuarioEntity.getSenha())
                .roles(roles)
                .build();
    }
}
