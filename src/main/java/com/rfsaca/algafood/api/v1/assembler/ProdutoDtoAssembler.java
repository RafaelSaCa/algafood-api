package com.rfsaca.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.v1.AlgaLinks;
import com.rfsaca.algafood.api.v1.controllers.ProdutoController;
import com.rfsaca.algafood.api.v1.model.ProdutoDto;
import com.rfsaca.algafood.domain.models.Produto;

@Component
public class ProdutoDtoAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDto> {

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public ProdutoDtoAssembler() {
        super(ProdutoController.class, ProdutoDto.class);
    }

    @Override
    public ProdutoDto toModel(Produto produto) {
        ProdutoDto produtoDto = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, ProdutoDto.class);

        produtoDto.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
        return produtoDto;
    }

}
