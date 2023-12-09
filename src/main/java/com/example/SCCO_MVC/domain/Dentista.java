package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dentista extends Pessoa {
    private String cro;
    private Especialidade especialidade;
    private List<Expediente> expedientes;

    public void validate() {
        if (this.getNome() == null || this.getNome().trim().equals("") || this.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou inválido");
        }
        if (this.getEspecialidade() == null) {
            throw new RegraNegocioException("Especialidade inválida");
        }
        if (this.getCro().length() != 7) {
            throw new RegraNegocioException("Cro inválido, diferente de 7 dígitos");
        }
        if (this.getCpf().length() != 14) {
            throw new RegraNegocioException("CPF inválido, número de dígitos incorreto");
        }
        if (this.getDataDeNascimento().getYear() > LocalDate.now().minusYears(18).getYear()) {
            throw new RegraNegocioException("Data de nascimento inválida, dentista menor de idade");
        }
    }
}
