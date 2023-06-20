package com.example.SCCO_MVC.api.controller;

import com.example.SCCO_MVC.api.dto.UsuarioDTO;
import com.example.SCCO_MVC.api.dto.TokenDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.exception.SenhaInvalidaException;
import com.example.SCCO_MVC.model.entity.Usuario;
import com.example.SCCO_MVC.security.JwtService;
import com.example.SCCO_MVC.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.annotations.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Api("API de Usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @GetMapping()
    public ResponseEntity get() {
        List<Usuario> usuarios = service.getUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody Usuario usuario ){
        try {
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
            usuario = service.salvar(usuario);
            return new ResponseEntity(usuario, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Usuario converter(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Usuario.class);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody UsuarioDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = service.autenticar(usuario,passwordEncoder);
            System.out.println(0);
            String token = jwtService.gerarToken(usuario);
            System.out.println(1);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
