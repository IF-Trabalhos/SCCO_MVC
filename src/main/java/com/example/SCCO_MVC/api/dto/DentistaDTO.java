package com.example.SCCO_MVC.api.dto;

import com.example.SCCO_MVC.model.entity.Dentista;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistaDTO {
    private Long id;
    private String cro;
    private String nome;
    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;
    private String complemento;
    private String cep;
    private String numero;

    public static DentistaDTO create(Dentista dentista){
        ModelMapper modelMapper = new ModelMapper();
        DentistaDTO dto = modelMapper.map(dentista, DentistaDTO.class);
        dto.logradouro = dentista.getEndereco().getLogradouro();
        dto.bairro = dentista.getEndereco().getBairro();
        dto.uf = dentista.getEndereco().getUf();
        dto.cidade = dentista.getEndereco().getCidade();
        dto.complemento = dentista.getEndereco().getComplemento();
        dto.cep = dentista.getEndereco().getCep();
        dto.numero = dentista.getEndereco().getNumero();
        return dto;
    }
}
