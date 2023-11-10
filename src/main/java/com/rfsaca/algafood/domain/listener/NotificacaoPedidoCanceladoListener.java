package com.rfsaca.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.rfsaca.algafood.domain.event.PedidoConfirmadoEvent;
import com.rfsaca.algafood.domain.models.Pedido;
import com.rfsaca.algafood.domain.services.EnvioEmailService;
import com.rfsaca.algafood.domain.services.EnvioEmailService.Mensagem;

@Component
public class NotificacaoPedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmailService;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido cancelado!")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);

    }

}
