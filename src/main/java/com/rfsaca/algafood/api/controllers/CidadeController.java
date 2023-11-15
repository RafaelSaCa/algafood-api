package com.rfsaca.algafood.api.controllers;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.rfsaca.algafood.api.ResourceUriHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.assembler.CidadeDtoAssembler;
import com.rfsaca.algafood.api.assembler.CidadeInputDisassembler;
import com.rfsaca.algafood.api.model.CidadeDto;
import com.rfsaca.algafood.api.model.input.CidadeInput;
import com.rfsaca.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.models.Cidade;
import com.rfsaca.algafood.domain.repositories.CidadeRepository;
import com.rfsaca.algafood.domain.services.CidadeService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public List<CidadeDto> listar() {
        List<Cidade> todasCidades = cidadeRepository.findAll();

        return cidadeDtoAssembler.toCollectionDto(todasCidades);
    }

    @GetMapping("/{cidadeId}")
    public CidadeDto buscar(@PathVariable Long cidadeId) {
        Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);

        return cidadeDtoAssembler.toDto(cidade);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDto adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cidadeService.salvar(cidade);

            CidadeDto cidadeDto= cidadeDtoAssembler.toDto(cidade);

            ResourceUriHelper.addUriResponseHeader(cidadeDto.getId());

          return  cidadeDto;
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
