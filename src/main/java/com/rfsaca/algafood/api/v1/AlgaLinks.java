package com.rfsaca.algafood.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.server.core.LinkBuilderSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.v1.controllers.CidadeController;
import com.rfsaca.algafood.api.v1.controllers.CozinhaController;
import com.rfsaca.algafood.api.v1.controllers.EstadoController;
import com.rfsaca.algafood.api.v1.controllers.FluxoPedidoController;
import com.rfsaca.algafood.api.v1.controllers.FormaPagamentoController;
import com.rfsaca.algafood.api.v1.controllers.GrupoController;
import com.rfsaca.algafood.api.v1.controllers.PedidoController;
import com.rfsaca.algafood.api.v1.controllers.ProdutoController;
import com.rfsaca.algafood.api.v1.controllers.RestauranteController;
import com.rfsaca.algafood.api.v1.controllers.RestauranteFormaPagamentoController;
import com.rfsaca.algafood.api.v1.controllers.RestauranteUsuarioResponsavelController;
import com.rfsaca.algafood.api.v1.controllers.UsuarioController;
import com.rfsaca.algafood.api.v1.controllers.UsuarioGrupoController;
import com.rfsaca.algafood.api.v1.model.GrupoDto;

import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;

@Component
public class AlgaLinks {

        public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
                        new TemplateVariable("page", VariableType.REQUEST_PARAM),
                        new TemplateVariable("size", VariableType.REQUEST_PARAM),
                        new TemplateVariable("sort", VariableType.REQUEST_PARAM));

        public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
                        new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

        public Link linkToPedidos(String rel) {
                TemplateVariables filtroVariables = new TemplateVariables(
                                new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
                                new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
                                new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
                                new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));

                String pedidosUrl = linkTo(PedidoController.class).toUri().toString();

                return Link.of(UriTemplate.of(pedidosUrl,
                                PAGINACAO_VARIABLES.concat(filtroVariables)), rel);

        }

        public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
                return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido))
                                .withRel(rel);
        }

        public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
                return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido))
                                .withRel(rel);
        }

        public Link linkToEntregaPedido(String codigoPedido, String rel) {
                return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido))
                                .withRel(rel);
        }

        public Link linkToRestaurante(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteController.class)
                                .buscar(restauranteId)).withRel(rel);
        }

        public Link linkToRestaurantes(String rel) {
                String restauranteUrl = linkTo(RestauranteController.class).toUri().toString();

                return Link.of(UriTemplate.of(restauranteUrl, PROJECAO_VARIABLES), rel);
        }

        public Link linkToRestaurantes() {
                return linkToRestaurantes(IanaLinkRelations.SELF.value());
        }

        public Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                                .listar(restauranteId)).withRel(rel);
        }

        public Link linkToRestauranteAbertura(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteController.class)
                                .abrir(restauranteId)).withRel(rel);
        }

        public Link linkToRestauranteFechamento(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteController.class)
                                .fechar(restauranteId)).withRel(rel);
        }

        public Link linkToRestauranteInativacao(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteController.class)
                                .inativar(restauranteId)).withRel(rel);
        }

        public Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteController.class)
                                .ativar(restauranteId)).withRel(rel);
        }

        public Link linkToRestauranteFormasPagamento(Long restauranteId) {
                return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
        }

        public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId,
                        String rel) {
                return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                                .desassociar(restauranteId, formaPagamentoId)).withRel(rel);
        }

        public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                                .associar(restauranteId, null)).withRel(rel);
        }

        public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId,
                        Long usuarioId, String rel) {
                return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                                .desassociar(restauranteId, usuarioId)).withRel(rel);
        }

        public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                                .associar(restauranteId, null)).withRel(rel);
        }

     
        public Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
                return linkTo(methodOn(FormaPagamentoController.class)
                                .buscar(formaPagamentoId, null)).withRel(rel);
        }

        public Link linkToFormaPagamento(Long formaPagamentoId) {
                return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
        }

        public Link linkToFormasPagamento(String rel) {
                return linkTo(FormaPagamentoController.class).withRel(rel);
        }

        public Link linkToFormasPagamento() {
                return linkToFormasPagamento(IanaLinkRelations.SELF.value());
        }

        public Link linkToCozinha(Long cozinhaId, String rel) {
                return linkTo(methodOn(CozinhaController.class)
                                .buscar(cozinhaId)).withRel(rel);
        }

        public Link linkToCozinha(Long cozinhaId) {
                return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
        }

        public Link linkToRestaurante(Long restauranteId) {
                return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
        }

        public Link linkToUsuario(Long usuarioId, String rel) {
                return linkTo(methodOn(UsuarioController.class)
                                .buscar(usuarioId)).withRel(rel);
        }

        public Link linkToUsuario(Long usuarioId) {
                return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
        }

        public Link linkToUsuarios(String rel) {
                return linkTo(UsuarioController.class).withRel(rel);
        }

        public Link linkToUsuarios() {
                return linkToUsuarios(IanaLinkRelations.SELF.value());
        }

        public Link linkToGrupos(String rel) {
                return linkTo(GrupoController.class).withRel(rel);
        }

        public Link linkToGruposPermissoes(Long grupoId, String rel) {
                return lintoTo(methodOn(GrupoController.class).listar(grupoId)).withRel(rel);
        }

        private LinkBuilderSupport<WebMvcLinkBuilder> lintoTo(CollectionModel<GrupoDto> listar) {
                return null;
        }

        public Link linkToGruposUsuario(Long usuarioId, String rel) {
                return linkTo(methodOn(UsuarioGrupoController.class)
                                .listar(usuarioId)).withRel(rel);
        }

        public Link linkToGruposUsuario(Long usuarioId) {
                return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
        }

        public Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
                return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                                .listar(restauranteId)).withRel(rel);
        }

        public Link linkToResponsaveisRestaurante(Long restauranteId) {
                return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
        }

        public Link linkToCidade(Long cidadeId, String rel) {
                return linkTo(methodOn(CidadeController.class)
                                .buscar(cidadeId)).withRel(rel);
        }

        public Link linkToCidade(Long cidadeId) {
                return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
        }

        public Link linkToCidades(String rel) {
                return linkTo(CidadeController.class).withRel(rel);
        }

        public Link linkToCidades() {
                return linkToCidades(IanaLinkRelations.SELF.value());
        }

        public Link linkToEstado(Long estadoId, String rel) {
                return linkTo(methodOn(EstadoController.class)
                                .buscar(estadoId)).withRel(rel);
        }

        public Link linkToEstado(Long estadoId) {
                return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
        }

        public Link linkToEstados(String rel) {
                return linkTo(EstadoController.class).withRel(rel);
        }

        public Link linkToEstados() {
                return linkToEstados(IanaLinkRelations.SELF.value());
        }

        public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
                return linkTo(methodOn(ProdutoController.class)
                                .buscar(restauranteId, produtoId))
                                .withRel(rel);
        }

        public Link linkToProduto(Long restauranteId, Long produtoId) {
                return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
        }

        public Link linkToProdutos(Long restauranteId, String rel) {
                return linkTo(methodOn(ProdutoController.class).listar(restauranteId, null)).withRel(rel);
        }

        public Link linkToProdutos(Long restauranteId) {
                return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
        }

        public Link linkToCozinhas(String rel) {
                return linkTo(CozinhaController.class).withRel(rel);
        }

        public Link linkToCozinhas() {
                return linkToCozinhas(IanaLinkRelations.SELF.value());
        }

}
