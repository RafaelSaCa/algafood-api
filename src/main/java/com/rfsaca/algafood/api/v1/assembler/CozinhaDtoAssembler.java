package com.rfsaca.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.v1.AlgaLinks;
import com.rfsaca.algafood.api.v1.controllers.CozinhaController;
import com.rfsaca.algafood.api.v1.model.CozinhaDto;
import com.rfsaca.algafood.domain.models.Cozinha;

@Component
public class CozinhaDtoAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public CozinhaDtoAssembler() {
        super(CozinhaController.class, CozinhaDto.class);

    }

    @Override
    public CozinhaDto toModel(Cozinha cozinha) {
        CozinhaDto cozinhaDto = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaDto);

        cozinhaDto.add(algaLinks.linkToCozinhas("cozinhas"));
        return cozinhaDto;
    }

}
