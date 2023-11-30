package com.rfsaca.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.v1.AlgaLinks;
import com.rfsaca.algafood.api.v2.AlgaLinksV2;
import com.rfsaca.algafood.api.v2.controller.CidadeControllerV2;
import com.rfsaca.algafood.api.v2.model.CidadeDtoV2;
import com.rfsaca.algafood.domain.models.Cidade;

@Component
public class CidadeDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDtoV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CidadeDtoAssemblerV2() {
        super(CidadeControllerV2.class, CidadeDtoV2.class);

    }

    @Override
    public CidadeDtoV2 toModel(Cidade cidade) {
        CidadeDtoV2 cidadeDto = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeDto);

        cidadeDto.add(algaLinks.linkToCidades("cidades"));

        return cidadeDto;
    }

    @Override
    public CollectionModel<CidadeDtoV2> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToCidades());
    }

}
