package com.rfsaca.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.EstadoDto;
import com.rfsaca.algafood.domain.models.Estado;

@Component
public class EstadoDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoDto toDto(Estado estado) {
        return modelMapper.map(estado, EstadoDto.class);
    }

    public List<EstadoDto> toCollectionDto(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toDto(estado))
                .collect(Collectors.toList());
    }
}