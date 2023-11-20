package com.rfsaca.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.CozinhaController;
import com.rfsaca.algafood.api.model.CozinhaDto;
import com.rfsaca.algafood.domain.models.Cozinha;

@Component
public class CozinhaDtoAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDto> {

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public CozinhaDtoAssembler() {
        super(CozinhaController.class, CozinhaDto.class);

    }

    @Override
    public CozinhaDto toModel(Cozinha cozinha) {
        CozinhaDto cozinhaDto = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, CozinhaDto.class);

        cozinhaDto.add(algaLinks.linkToCozinhas("cozinhas"));
        return cozinhaDto;
    }

}
