package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnderecoTest {
    Endereco validEndereco =
            new Endereco("Logradouro",
                    "bairro",
                    "uf",
                    "cidade",
                    "numero",
                    "complemento",
                    "123456789");

    Endereco endereco;

    @BeforeEach
    void setUp(){
        endereco = validEndereco;
    }

    @Test
    void shouldReturnValidEndereco() {
        assertDoesNotThrow(() ->
                endereco.validate());
    }
    @Test
    void shouldReturnInvalidEnderecoLogradouroNull() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setLogradouro(null);
                    endereco.validate();
                });
        assertEquals("Logradouro vazio ou invalido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoLogradouroEmpty() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setLogradouro("");
                    endereco.validate();
                });
        assertEquals("Logradouro vazio ou invalido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoLogradouroBigger() {
        String string = "a";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setLogradouro(string.repeat(256));
                    endereco.validate();
                });
        assertEquals("Logradouro vazio ou invalido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoBairroNull() {
        String string = "a";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setBairro(null);
                    endereco.validate();
                });
        assertEquals("Bairro invalido ou vazio", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidEnderecoBairroEmpty() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setBairro("");
                    endereco.validate();
                });
        assertEquals("Bairro invalido ou vazio", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoBairroBigger() {
        String string = "a";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setBairro(string.repeat(256));
                    endereco.validate();
                });
        assertEquals("Bairro invalido ou vazio", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoCepNull() {
        String string = "a";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setCep(null);
                    endereco.validate();
                });
        assertEquals("Cep invalido ou vazio", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidEnderecoCepEmpty() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setCep("");
                    endereco.validate();
                });
        assertEquals("Cep invalido ou vazio", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoBairroCepCount() {
        String string = "a";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setCep(string.repeat(10));
                    endereco.validate();
                });
        assertEquals("Cep invalido ou vazio", exception.getMessage());
    }

    @Test
    void shouldReturnInvalidEnderecoNumeroShort() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setNumero("");
                    endereco.validate();
        });
        assertEquals("Número inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoNumeroBigger() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setNumero("12345678900");
                    endereco.validate();
                });
        assertEquals("Número inválido", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoCidadeEmpty() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setCidade("");
                    endereco.validate();
                });
        assertEquals("Cidade inválida, campo vazio ou com digitos em excesso", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoCidadeBigger() {
        String string = "a";
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setCidade(string.repeat(256));
                    endereco.validate();
                });
        assertEquals("Cidade inválida, campo vazio ou com digitos em excesso", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidEnderecoUfDigitCount() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    endereco.setUf("abc");
                    endereco.validate();
                });
        assertEquals("UF inválida", exception.getMessage());
    }
}
