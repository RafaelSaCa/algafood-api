package com.rfsaca.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.FormaPagamentoDto;
import com.rfsaca.algafood.domain.models.FormaPagamento;

@Component
public class FormaPagamentoDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoDto toDto(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDto.class);
    }

    public List<FormaPagamentoDto> toCollectionDto(List<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
                .map(formaPagamento -> toDto(formaPagamento)).collect(Collectors.toList());

    }

}
