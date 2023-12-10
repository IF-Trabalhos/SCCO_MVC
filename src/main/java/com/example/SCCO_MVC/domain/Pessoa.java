package com.example.SCCO_MVC.domain;

import java.time.LocalDate;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    private String nome;
    private String cpf;
    private String telefone;
    private LocalDate dataDeNascimento;
    private String rg;
    private String email;
    private boolean ativo;
    private Endereco endereco;

    public void validate() {
        if (this.getNome() == null) {
            throw new RegraNegocioException("Nome não pode ser nulo");
        }
        if (this.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Nome não pode estar vazio");
        }
        if (this.getNome().length() > 255) {
            throw new RegraNegocioException("Nome excede o limite de caracteres");
        }
        if (this.getCpf() == null) {
            throw new RegraNegocioException("CPF não pode ser nulo");
        }
        if (this.getCpf().length() != 14) {
            throw new RegraNegocioException("CPF inválido, número de dígitos incorreto");
        }
        if (!this.getCpf().matches("[0-9.-]+")) {
            throw new RegraNegocioException("CPF inválido, contém digito inválidos");
        }
        if (this.getTelefone() == null) {
            throw new RegraNegocioException("Telefone não pode ser nulo");
        }
        if (this.getTelefone().length() < 8) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if (this.getDataDeNascimento() == null)
            throw new RegraNegocioException("Data de nascimento inválida");
        if (this.getDataDeNascimento().isAfter(LocalDate.now())){
            throw new RegraNegocioException("Data de nascimento inválida");
        }
        if (this.getRg() == null){
            throw new RegraNegocioException("RG não pode ser nulo");
        }
        if (this.getRg().trim().isEmpty()){
            throw new RegraNegocioException("RG não pode estar vazio");
        }
        if (this.getRg().length() > 20) {
            throw new RegraNegocioException("RG excede o limite de caracteres");
        }
        if (this.getEmail() == null){
            throw new RegraNegocioException("E-mail não pode ser nulo");
        }
        if (!this.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new RegraNegocioException("E-mail inválido");
        }
        if (this.getEndereco() == null) {
            throw new RegraNegocioException("Endereço não pode ser nulo");
        }
    }
}
