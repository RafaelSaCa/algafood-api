package com.rfsaca.algafood.domain.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.exceptions.PedidoNaoEncontradoException;
import com.rfsaca.algafood.domain.models.Cidade;
import com.rfsaca.algafood.domain.models.FormaPagamento;
import com.rfsaca.algafood.domain.models.Pedido;
import com.rfsaca.algafood.domain.models.Produto;
import com.rfsaca.algafood.domain.models.Restaurante;
import com.rfsaca.algafood.domain.models.Usuario;
import com.rfsaca.algafood.domain.repositories.PedidoRepository;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    @Autowired
    private CidadeService cidadeService;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    public void validarPedido(Pedido pedido) {
        Usuario cliente = usuarioService.buscarOuFalhar(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        Cidade cidade = cidadeService.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    public void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
