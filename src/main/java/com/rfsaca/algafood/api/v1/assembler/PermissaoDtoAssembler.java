package com.rfsaca.algafood.api.v1.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.v1.model.PermissaoDto;
import com.rfsaca.algafood.domain.models.Permissao;

@Component
public class PermissaoDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDto toDto(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoDto.class);
    }

    public List<PermissaoDto> toCollectionDto(Collection<Permissao> permissoes) {
        return permissoes.stream()
                .map(permissao -> toDto(permissao))
                .collect(Collectors.toList());
    }
}
