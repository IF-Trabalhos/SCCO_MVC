package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Secretaria;
import org.modelmapper.ModelMapper;

public class SecretariaDTO {
    private Long id;
    private String nome;
    private double salario;
    private String pis;
    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;
    private String complemento;
    private String cep;
    private Integer numero;
    public static SecretariaDTO create(Secretaria secretatia){
        ModelMapper modelMapper = new ModelMapper();
        SecretariaDTO dto = modelMapper.map(secretatia, SecretariaDTO.class);
        dto.logradouro = secretatia.getEndereco().getLogradouro();
        dto.bairro = secretatia.getEndereco().getBairro();
        dto.uf = secretatia.getEndereco().getUf();
        dto.cidade = secretatia.getEndereco().getCidade();
        dto.complemento = secretatia.getEndereco().getComplemento();
        dto.cep = secretatia.getEndereco().getCep();
        dto.numero = secretatia.getEndereco().getNumero();
        return dto;
    }
}
