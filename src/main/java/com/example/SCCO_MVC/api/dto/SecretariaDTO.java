package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Secretaria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecretariaDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private Date dataDeNascimento;
    private String rg;
    private String email;
    private Double salario;
    private String pis;
    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;
    private String complemento;
    private String cep;
    private String numero;
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
