package com.rfsaca.algafood.api.v2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.ResourceUriHelper;
import com.rfsaca.algafood.api.v2.assembler.CidadeDtoAssemblerV2;
import com.rfsaca.algafood.api.v2.assembler.CidadeInputDisassemblerV2;
import com.rfsaca.algafood.api.v2.model.CidadeDtoV2;
import com.rfsaca.algafood.api.v2.model.input.CidadeInputV2;
import com.rfsaca.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.models.Cidade;
import com.rfsaca.algafood.domain.repositories.CidadeRepository;
import com.rfsaca.algafood.domain.services.CidadeService;

@RestController
@RequestMapping(path = "/v2/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeControllerV2 {

    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeDtoAssemblerV2 cidadeDtoAssembler;
    @Autowired
    private CidadeInputDisassemblerV2 cidadeInputDisassembler;

    @GetMapping
    public CollectionModel<CidadeDtoV2> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        return cidadeDtoAssembler.toCollectionModel(todasCidades);

    }

    @GetMapping("/{cidadeId}")
    public CidadeDtoV2 buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        return cidadeDtoAssembler.toModel(cidade);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDtoV2 adicionar(@RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cidadeService.salvar(cidade);

            CidadeDtoV2 cidadeDto = cidadeDtoAssembler.toModel(cidade);

            ResourceUriHelper.addUriResponseHeader(cidadeDto.getIdCidade());

            return cidadeDto;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDtoV2 atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputV2 cidadeInput) {
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeDtoAssembler.toModel(cidadeAtual);

        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);

    }
}
