package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import com.example.SCCO_MVC.model.entity.PacienteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente extends Pessoa {
    private String numProntuario;

    public void validate() {
        if (this.getNumProntuario() == null || this.getNumProntuario().trim().equals("")
                || this.getNumProntuario().length() > 14) {
            throw new RegraNegocioException("Numero de Prontuario vazio ou invalido");
        }
        if (this.getNome() == null || this.getNome().trim().equals("")
                || this.getNome().length() > 255) {
            throw new RegraNegocioException("Nome vazio ou invalido");
        }
        if (this.getCpf().length() != 14){
            throw new RegraNegocioException("CPF inválido, número de digitos incorreto");
        }
        if (this.getDataDeNascimento().getYear() < LocalDate.now().minusMonths(6).getYear()
                || (this.getDataDeNascimento().getYear() == LocalDate.now().minusMonths(6).getYear()
                && this.getDataDeNascimento().getMonthValue() > LocalDate.now().minusMonths(6).getMonthValue())){
            throw new RegraNegocioException("Data de nascimento inválida, paciente com menos de 6 meses");
        }
        if (this.getRg().length() > 10 || this.getRg().trim().equals("") ){
            throw new RegraNegocioException("Rg inválido, número de digitos maior que 10");
        }
    }
}
