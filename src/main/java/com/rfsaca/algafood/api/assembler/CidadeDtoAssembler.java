package com.rfsaca.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.CidadeDto;
import com.rfsaca.algafood.domain.models.Cidade;

@Component
public class CidadeDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeDto toDto(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDto.class);
    }

    public List<CidadeDto> toCollectionDto(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toDto(cidade))
                .collect(Collectors.toList());
    }
}
