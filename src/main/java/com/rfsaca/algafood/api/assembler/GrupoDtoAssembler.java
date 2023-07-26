package com.rfsaca.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.GrupoDto;
import com.rfsaca.algafood.domain.models.Grupo;

@Component
public class GrupoDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoDto toDto(Grupo grupo) {
        return modelMapper.map(grupo, GrupoDto.class);

    }

    public List<GrupoDto> toCollectionDto(List<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toDto(grupo))
                .collect(Collectors.toList());
    }

}
