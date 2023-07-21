package com.rfsaca.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.CozinhaDto;
import com.rfsaca.algafood.domain.models.Cozinha;

@Component
public class CozinhaDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaDto toDto(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDto.class);
    }

    public List<CozinhaDto> toCollectionDto(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toDto(cozinha))
                .collect(Collectors.toList());
    }
}
