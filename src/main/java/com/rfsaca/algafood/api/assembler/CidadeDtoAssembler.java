package com.rfsaca.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.CidadeController;
import com.rfsaca.algafood.api.model.CidadeDto;
import com.rfsaca.algafood.domain.models.Cidade;

@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDto> {

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public CidadeDtoAssembler() {
        super(CidadeController.class, CidadeDto.class);

    }

    @Override
    public CidadeDto toModel(Cidade cidade) {
        CidadeDto cidadeDto = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeDto);

        cidadeDto.add(algaLinks.linkToCidades("cidades"));
        cidadeDto.getEstado().add(algaLinks.linkToEstado(cidadeDto.getEstado().getId()));

        return cidadeDto;
    }

    @Override
    public CollectionModel<CidadeDto> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToCidades());
    }

}
