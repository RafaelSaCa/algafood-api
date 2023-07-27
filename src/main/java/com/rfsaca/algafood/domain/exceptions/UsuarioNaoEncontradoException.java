package com.rfsaca.algafood.domain.exceptions;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);

    }

    public UsuarioNaoEncontradoException(Long usuarioid) {
        this(String.format("Não existe um usuário com o código %d", usuarioid));
    }
}
