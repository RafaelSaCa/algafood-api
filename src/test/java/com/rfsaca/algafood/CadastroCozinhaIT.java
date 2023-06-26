package com.rfsaca.algafood;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rfsaca.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.rfsaca.algafood.domain.exceptions.EntidadeEmUsoException;
import com.rfsaca.algafood.domain.models.Cozinha;
import com.rfsaca.algafood.domain.services.CozinhaService;

@SpringBootTest
public class CadastroCozinhaIT {// TESTE DE INTEGRAÇÃO

    @Autowired
    private CozinhaService cozinhaService;

    @Test
    public void testarCadastroCozinhaComSucesso() {
        // cenario
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // ação
        novaCozinha = cozinhaService.salvar(novaCozinha);

        // validação
        assertNotNull(novaCozinha);
        assertNotNull(novaCozinha.getId());
    }

    @Test
    public void testarCadastroCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado = assertThrows(ConstraintViolationException.class, () -> {
            cozinhaService.salvar(novaCozinha);
        });
        assertNotNull(erroEsperado);
    }

    @Test
    public void deveFalharAoExcluirCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado = assertThrows(EntidadeEmUsoException.class, () -> {
            cozinhaService.excluir(1L);
        });
        assertNotNull(erroEsperado);
    }

    @Test
    public void deveFalharAoExcluirCozinhaInexistente() {

        CozinhaNaoEncontradaException erroEsperado = assertThrows(CozinhaNaoEncontradaException.class, () -> {
            cozinhaService.excluir(100L);
        });
        assertNotNull(erroEsperado);

    }
}
