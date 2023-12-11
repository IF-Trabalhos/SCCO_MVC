package com.example.SCCO_MVC.domain;

import com.example.SCCO_MVC.exception.RegraNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DentistaTest {

    Dentista validDentista =
            new Dentista("1234567",
                    new Especialidade(),
                    (List<Expediente>) new ArrayList());


    Dentista dentista;
    @BeforeEach
    public void init() {
        dentista = validDentista;
        dentista.setNome("Pessoa");
        dentista.setCpf("123.456.678-90");
        dentista.setTelefone("32999999999");
        dentista.setDataDeNascimento(LocalDate.now().minusYears(20));
        dentista.setRg("124456778");
        dentista.setEmail("pessoa@gmail.com");
        dentista.setAtivo(true);
        dentista.setEndereco(new Endereco());
    }
    @Test
    void shouldReturnValidDentista() {
        assertDoesNotThrow(() -> dentista.validate());
    }
    @Test
    void shouldReturnInvalidDentistaEspecialidade(){
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    dentista.setEspecialidade(null);
                    dentista.validate();
                });
        assertEquals("Especialidade inválida", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidDentistaCro() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    dentista.setCro("123456");
                    dentista.validate();
                });
        assertEquals("Cro inválido, diferente de 7 dígitos", exception.getMessage());
    }
    @Test
    void shouldReturnInvalidDentistaCroDigitsInvalid(){
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    dentista.setCro("@@@@@@@");
                    dentista.validate();
                });
        assertEquals("CRO inválido, deve conter apenas números", exception.getMessage());
    }
   @Test
    void shouldReturnInvalidDentistaUnderage() {
        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class, () -> {
                    dentista.setDataDeNascimento(LocalDate.now().minusYears(17));
                    dentista.validate();
                });
        assertEquals("Data de nascimento inválida, dentista menor de idade", exception.getMessage());
    }
}
