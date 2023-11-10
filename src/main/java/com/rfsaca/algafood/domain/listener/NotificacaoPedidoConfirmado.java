package com.rfsaca.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.domain.event.PedidoConfirmadoEvent;
import com.rfsaca.algafood.domain.models.Pedido;
import com.rfsaca.algafood.domain.services.EnvioEmailService;
import com.rfsaca.algafood.domain.services.EnvioEmailService.Mensagem;

@Component
public class NotificacaoPedidoConfirmado {

    @Autowired
    private EnvioEmailService envioEmailService;

    @EventListener
    private void aoConfirmarPedido(PedidoConfirmadoEvent event) {

        Pedido pedido = event.getPedido();
        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado!")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)

                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmailService.enviar(mensagem);
    }
}
