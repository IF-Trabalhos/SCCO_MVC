package com.example.SCCO_MVC.controller;

import com.example.SCCO_MVC.dto.UsuarioDTO;
import com.example.SCCO_MVC.dto.TokenDTO;
import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.exception.SenhaInvalidaException;
import com.example.SCCO_MVC.model.entity.UsuarioEntity;
import com.example.SCCO_MVC.security.JwtService;
import com.example.SCCO_MVC.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.annotations.*;

import java.util.List;

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
    public ResponseEntity<List<UsuarioDTO>> get(){
        List<UsuarioDTO> usuarios = service.get();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody UsuarioEntity entity){
        try {
            String senhaCriptografada = passwordEncoder.encode(entity.getSenha());
            entity.setSenha(senhaCriptografada);
             UsuarioDTO usuarioDTO = service.save(UsuarioDTO.fromEntityToDTO(entity));
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDTO);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody UsuarioDTO credenciais){
        try{
            UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            service.autenticar(usuarioEntity,passwordEncoder);
            String token = jwtService.gerarToken(usuarioEntity);
            return new TokenDTO(usuarioEntity.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
