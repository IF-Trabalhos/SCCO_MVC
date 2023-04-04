package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long id;
    private String nome;
    private String numProntuario;
    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;
    private String complemento;
    private String cep;
    private Integer numero;


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
        return dto;
    }
}
