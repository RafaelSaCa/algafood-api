package com.rfsaca.algafood.domain.exceptions;

public class CozinhaNaoEncontradaException extends NegocioException {

    public CozinhaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long cozinhaId) {
        this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
    }

}