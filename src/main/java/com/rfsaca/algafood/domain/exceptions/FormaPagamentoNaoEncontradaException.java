package com.rfsaca.algafood.domain.exceptions;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        this(String.format("Não existe um cadastro de forma de pagamento com o código %id", formaPagamentoId));
    }
}
