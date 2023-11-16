package com.rfsaca.algafood.api.controllers;

import com.rfsaca.algafood.api.ResourceUriHelper;
import com.rfsaca.algafood.api.assembler.CidadeDtoAssembler;
import com.rfsaca.algafood.api.assembler.CidadeInputDisassembler;
import com.rfsaca.algafood.api.model.CidadeDto;
import com.rfsaca.algafood.api.model.input.CidadeInput;
import com.rfsaca.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.models.Cidade;
import com.rfsaca.algafood.domain.repositories.CidadeRepository;
import com.rfsaca.algafood.domain.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeDtoAssembler cidadeDtoAssembler;
    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @GetMapping
    public CollectionModel<CidadeDto> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        List<CidadeDto> cidadeDtos = cidadeDtoAssembler.toCollectionDto(todasCidades);

        cidadeDtos.forEach(cidadeDto -> {
            cidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class).slash(cidadeDto.getId()).withSelfRel());

            cidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withRel("cidades"));

            cidadeDto.getEstado().add(
                    WebMvcLinkBuilder.linkTo(EstadoController.class).slash(cidadeDto.getEstado().getId())
                            .withSelfRel());

        });
        CollectionModel<CidadeDto> cidadesCollectionModel = CollectionModel.of(cidadeDtos);

        cidadesCollectionModel.add(linkTo(CidadeController.class).withSelfRel());
        return cidadesCollectionModel;
    }

    @GetMapping("/{cidadeId}")
    public CidadeDto buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        CidadeDto cidadeDto = cidadeDtoAssembler.toDto(cidade);

        /*
         * cidadeDto.add(linkTo(methodOn(CidadeController.class)
         * .buscar(cidadeDto.getId())).withSelfRel());
         * 
         * cidadeDto.add(linkTo(methodOn(CidadeController.class)
         * .listar()).withRel("cidades"));
         * 
         * cidadeDto.getEstado().add(linkTo(methodOn(EstadoController.class)
         * .buscar(cidadeDto.getEstado().getId())).withSelfRel());
         */

        cidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class).slash(cidadeDto.getId()).withSelfRel());

        cidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withRel("cidades"));

        cidadeDto.getEstado().add(
                WebMvcLinkBuilder.linkTo(EstadoController.class).slash(cidadeDto.getEstado().getId()).withSelfRel());

        return cidadeDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDto adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cidadeService.salvar(cidade);

            CidadeDto cidadeDto = cidadeDtoAssembler.toDto(cidade);

            ResourceUriHelper.addUriResponseHeader(cidadeDto.getId());

            return cidadeDto;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDto atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);

            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeDtoAssembler.toDto(cidadeAtual);

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
