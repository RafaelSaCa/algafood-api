package com.rfsaca.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.FormaPagamentoController;
import com.rfsaca.algafood.api.model.FormaPagamentoDto;
import com.rfsaca.algafood.domain.models.FormaPagamento;

@Component
public class FormaPagamentoDtoAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDto> {

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public FormaPagamentoDtoAssembler() {
        super(FormaPagamentoController.class, FormaPagamentoDto.class);

    }

    @Override
    public FormaPagamentoDto toModel(FormaPagamento formaPagamento) {
        FormaPagamentoDto formaPagamentoDto = createModelWithId(formaPagamento.getId(), formaPagamento);
        modelMapper.map(formaPagamento, FormaPagamentoDto.class);

        formaPagamentoDto.add(algaLinks.linkToFormasPagamento("formasPagamento"));

        return formaPagamentoDto;
    }

    @Override
    public CollectionModel<FormaPagamentoDto> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToFormasPagamento());
    }

}
