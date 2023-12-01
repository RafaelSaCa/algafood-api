package com.rfsaca.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.rfsaca.algafood.api.v2.AlgaLinksV2;
import com.rfsaca.algafood.api.v2.controller.CozinhaControllerV2;
import com.rfsaca.algafood.api.v2.model.CozinhaDtoV2;
import com.rfsaca.algafood.domain.models.Cozinha;

@Component
public class CozinhaDtoAssemblerV2 extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDtoV2> {

    public CozinhaDtoAssemblerV2(Class<?> controllerClass, Class<CozinhaDtoV2> resourceType) {
        super(CozinhaControllerV2.class, CozinhaDtoV2.class);
    }

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    @Override
    public CozinhaDtoV2 toModel(Cozinha cozinha) {
        CozinhaDtoV2 cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));

        return cozinhaModel;
    }
}