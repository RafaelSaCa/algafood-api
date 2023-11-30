package com.rfsaca.algafood.api.v1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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

import com.rfsaca.algafood.api.v1.assembler.RestauranteApenasNomeDtoAssembler;
import com.rfsaca.algafood.api.v1.assembler.RestauranteBasicoDtoAssembler;
import com.rfsaca.algafood.api.v1.assembler.RestauranteDtoAssembler;
import com.rfsaca.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.rfsaca.algafood.api.v1.model.RestauranteApenasNomeDto;
import com.rfsaca.algafood.api.v1.model.RestauranteBasicoDto;
import com.rfsaca.algafood.api.v1.model.RestauranteDto;
import com.rfsaca.algafood.api.v1.model.input.RestauranteInput;
import com.rfsaca.algafood.domain.exceptions.CidadeNaoEncontradaException;
import com.rfsaca.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.rfsaca.algafood.domain.models.Restaurante;
import com.rfsaca.algafood.domain.repositories.RestauranteRepository;
import com.rfsaca.algafood.domain.services.RestauranteService;

@RestController
@RequestMapping("/v1/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private RestauranteDtoAssembler restauranteDtoAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    private RestauranteBasicoDtoAssembler restauranteBasicoDtoAssembler;

    private RestauranteApenasNomeDtoAssembler restauranteApenasNomeDtoAssembler;

    // @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public CollectionModel<RestauranteBasicoDto> listar() {
        return restauranteBasicoDtoAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    // @JsonView(RestauranteView.ApenasNome.class)
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeDto> listarApenasNomes() {
        return restauranteApenasNomeDtoAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    // @GetMapping
    // public MappingJacksonValue listar(@RequestParam(required = false) String
    // projecao) {
    // List<Restaurante> restaurantes = restauranteRepository.findAll();
    // List<RestauranteDto> restauranteDtos =
    // restauranteDtoAssembler.toCollectionDto(restaurantes);

    // MappingJacksonValue restauranteWrapper = new
    // MappingJacksonValue(restauranteDtos);

    // restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);

    // if ("apenas-nome".equals(projecao)) {
    // restauranteWrapper.setSerializationView(RestauranteView.ApenasNome.class);
    // } else if ("completo".equals(projecao)) {
    // restauranteWrapper.setSerializationView(null);
    // }

    // return restauranteWrapper;
    // }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteDtoAssembler.toModel(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getLocalizedMessage());
        }

    }

    @GetMapping("/{restauranteId}")
    public RestauranteDto buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return restauranteDtoAssembler.toModel(restaurante);

    }

    @PutMapping("/{restauranteId}")
    public RestauranteDto atualizar(@PathVariable Long restauranteId,
            @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteDtoAssembler.toModel(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getLocalizedMessage(), e);
        }

    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getLocalizedMessage(), e);
        }

    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        restauranteService.abrir(restauranteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        restauranteService.fechar(restauranteId);

        return ResponseEntity.noContent().build();
    }
}