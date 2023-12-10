package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {
    @Test
    void shouldThrowExceptionForNullName() {
        Pessoa pessoa = new Pessoa(null, "123.456.789-00", "12345678",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Nome não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmptyName() {
        Pessoa pessoa = new Pessoa("", "123.456.789-00", "12345678",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Nome não pode estar vazio", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForLongName() {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            longName.append("a");
        }

        Pessoa pessoa = new Pessoa(longName.toString(), "123.456.789-00", "12345678",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Nome excede o limite de caracteres", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullCpf() {
        Pessoa pessoa = new Pessoa("Nome", null, "12345678",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("CPF não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidCpfDigits() {
        Pessoa pessoa = new Pessoa("Nome", "123456789", "12345678",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("CPF inválido, número de dígitos incorreto", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidCpfDigitsContent() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-0A", "12345678",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("CPF inválido, contém digito inválidos", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullTelefone() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-00", null,
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Telefone não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidTelefoneLength() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-00", "123",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Telefone inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullDataDeNascimento() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-00", "12345678", null,
                "123456789", "email@example.com", true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Data de nascimento inválida", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForFutureDataDeNascimento() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-00", "12345678",
                LocalDate.of(2050, 1, 1), "123456789", "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Data de nascimento inválida", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullRg() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-00", "12345678",
                LocalDate.of(1990, 1, 1), null, "email@example.com",
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("RG não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForEmptyRg() {
        Pessoa pessoa =
                new Pessoa("Nome", "123.456.789-00", "12345678",
                        LocalDate.of(1990, 1, 1), "", "email@example.com",
                        true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("RG não pode estar vazio", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForLongRg() {
        String longRg = "12345678910111213141516";

        Pessoa pessoa =
                new Pessoa("Nome", "123.456.789-00", "12345678",
                        LocalDate.of(1990, 1, 1), longRg, "email@example.com",
                        true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("RG excede o limite de caracteres", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullEmail() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-00", "12345678",
                LocalDate.of(1990, 1, 1), "123456789", null,
                true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("E-mail não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidEmail() {
        Pessoa pessoa =
                new Pessoa("Nome", "123.456.789-00", "12345678",
                        LocalDate.of(1990, 1, 1), "123456789", "invalidemail",
                        true, new Endereco());

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("E-mail inválido", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullEndereco() {
        Pessoa pessoa = new Pessoa("Nome", "123.456.789-00", "12345678",
                LocalDate.of(1990, 1, 1), "123456789", "email@example.com",
                true, null);

        RegraNegocioException exception = assertThrows(RegraNegocioException.class, pessoa::validate);
        assertEquals("Endereço não pode ser nulo", exception.getMessage());
    }
}
