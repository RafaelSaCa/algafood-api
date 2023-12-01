package com.rfsaca.algafood.api.v2.assembler;

import org.modelmapper.ModelMapper;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.rfsaca.algafood.api.v2.model.input.CozinhaInputV2;
import com.rfsaca.algafood.domain.models.Cozinha;

@Component
public class CozinhaInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInputV2 cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInputV2 cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
}