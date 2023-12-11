package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {
    Pessoa validPessoa =
            new Pessoa(
                    "Pessoa",
                    "123.456.678-90",
                    "32999999999",
                    LocalDate.now().minusYears(20),
                    "124456778",
                    "pessoa@gmail.com",
                    true,
                    new Endereco());

    Pessoa pessoa;

    @BeforeEach
    public void init(){
        pessoa = validPessoa;
    }

    @Test
    void shouldReturnValidPessoa() {
        assertDoesNotThrow(() ->
                pessoa.validate());
    }

    @Test
    void sholdReturnInvalidNome() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setNome(null);
                    pessoa.validate();
                });
        assertEquals("Nome não pode ser nulo", exception.getMessage());
    }
    @Test
    void sholdReturnInvalidNomeEmpty() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setNome("");
                    pessoa.validate();
                });
        assertEquals("Nome não pode estar vazio", exception.getMessage());
    }
    @Test
    void sholdReturnInvalidNomeBigger() {
        String biggerName = "a";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setNome(biggerName.repeat(260));
                    pessoa.validate();
                });
        assertEquals("Nome excede o limite de caracteres", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidCpf() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setCpf(null);
                    pessoa.validate();
                });
        assertEquals("CPF não pode ser nulo", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidDigitCountCpf() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setCpf("1234567890123");
                    pessoa.validate();
                });
        assertEquals("CPF inválido, número de dígitos incorreto", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidCpfMatches() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setCpf("@@@@@@@@@@@@@@");
                    pessoa.validate();
                });
        assertEquals("CPF inválido, contém digito inválidos", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidTelefone() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setTelefone(null);
                    pessoa.validate();
                });
        assertEquals("Telefone não pode ser nulo", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidTelefoneWithLowerDigitCount() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setTelefone("1234567");
                    pessoa.validate();
                });
        assertEquals("Telefone inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidData() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setDataDeNascimento(null);
                    pessoa.validate();
                });
        assertEquals("Data de nascimento inválida", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidDataIsInFuture() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setDataDeNascimento(LocalDate.now().plusYears(10));
                    pessoa.validate();
                });
        assertEquals("Data de nascimento inválida", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidRg() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setRg(null);
                    pessoa.validate();
                });
        assertEquals("RG não pode ser nulo", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidRgEmpty() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setRg("");
                    pessoa.validate();
                });
        assertEquals("RG não pode estar vazio", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidRgBigger() {
        String string = "aaaaaaaaaaaaaaaaaaaaa";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setRg(string);
                    pessoa.validate();
                });
        assertEquals("RG excede o limite de caracteres", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEmail() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setEmail(null);
                    pessoa.validate();
                });
        assertEquals("E-mail não pode ser nulo", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEmailWithInvalidPattern() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setEmail("@@@$$!!!,");
                    pessoa.validate();
                });
        assertEquals("E-mail inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEndereco() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    pessoa.setEndereco(null);
                    pessoa.validate();
                });
        assertEquals("Endereço não pode ser nulo", exception.getMessage());
    }
}
