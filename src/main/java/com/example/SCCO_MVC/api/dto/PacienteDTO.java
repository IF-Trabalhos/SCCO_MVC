package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Paciente;
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
    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;
    private String complemento;
    private String cep;
    private String numero;
    private boolean ativo;


    public static PacienteDTO create(Paciente paciente){
        ModelMapper modelMapper = new ModelMapper();
        PacienteDTO dto = modelMapper.map(paciente, PacienteDTO.class);

        dto.logradouro = paciente.getEndereco().getLogradouro();
        dto.bairro = paciente.getEndereco().getBairro();
        dto.uf = paciente.getEndereco().getUf();
        dto.cidade = paciente.getEndereco().getCidade();
        dto.complemento = paciente.getEndereco().getComplemento();
        dto.cep = paciente.getEndereco().getCep();
        dto.numero = paciente.getEndereco().getNumero();
        dto.ativo = paciente.isAtivo();
        return dto;
    }
}
