package com.rfsaca.algafood.domain.exceptions;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
        // TODO Auto-generated constructor stub
    }

    public PermissaoNaoEncontradaException(Long permissaoId) {
        this(String.format("Não existe um cadastro de  permissão com o código %d", permissaoId));
    }
}
