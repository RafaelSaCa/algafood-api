package com.rfsaca.algafood.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.model.CozinhaDto;
import com.rfsaca.algafood.api.model.RestauranteDto;
import com.rfsaca.algafood.api.model.input.RestauranteInput;
import com.rfsaca.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.models.Cozinha;
import com.rfsaca.algafood.domain.models.Restaurante;
import com.rfsaca.algafood.domain.repositories.RestauranteRepository;
import com.rfsaca.algafood.domain.services.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<RestauranteDto> listar() {
        return toCollectionDto(restauranteRepository.findAll());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDto adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = toDomainObject(restauranteInput);

            return toDto(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getLocalizedMessage());
        }

    }

    @GetMapping("/{restauranteId}")
    public RestauranteDto buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return toDto(restaurante);

    }

    @PutMapping("/{restauranteId}")
    public RestauranteDto atualizar(@PathVariable Long restauranteId,
            @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = toDomainObject(restauranteInput);

            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
                    "produtos");
            return toDto(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }

    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long restauranteId) {
        restauranteService.excluir(restauranteId);
    }

    private RestauranteDto toDto(Restaurante restaurante) {
        RestauranteDto restauranteDto = new RestauranteDto();
        restauranteDto.setId(restaurante.getId());
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setTaxaFrete(restaurante.getTaxaFrete());

        CozinhaDto cozinhaDto = new CozinhaDto();
        cozinhaDto.setId(restaurante.getCozinha().getId());
        cozinhaDto.setNome(restaurante.getCozinha().getNome());

        restauranteDto.setCozinha(cozinhaDto);
        return restauranteDto;
    }

    private List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toDto(restaurante))
                .collect(Collectors.toList());

    }

    private Restaurante toDomainObject(RestauranteInput restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());

        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        return restaurante;

    }

    /*
     * @PatchMapping("/{restauranteId}")
     * public RestauranteDto atualizarParcial(@PathVariable Long
     * restauranteId, @RequestBody Map<String, Object> campos,
     * HttpServletRequest request) {
     * Restaurante restauranteAtual =
     * restauranteService.buscarOuFalhar(restauranteId);
     * 
     * merge(campos, restauranteAtual, request);
     * validate(restauranteAtual, "restaurante");
     * 
     * return atualizar(restauranteId, restauranteAtual);
     * }
     * 
     * 
     * private void validate(Restaurante restaurante, String objectName) {
     * BeanPropertyBindingResult bindingResult = new
     * BeanPropertyBindingResult(restaurante, null);
     * validator.validate(restaurante, bindingResult);
     * 
     * if (bindingResult.hasErrors()) {
     * throw new ValidacaoException(bindingResult);
     * }
     * 
     * }
     * 
     * private void merge(Map<String, Object> dadosOrigem, Restaurante
     * restauranteDestino, HttpServletRequest request) {
     * ServletServerHttpRequest serverHttpRequest = new
     * ServletServerHttpRequest(request);
     * 
     * try {
     * ObjectMapper objectMapper = new ObjectMapper();
     * objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,
     * true);
     * objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
     * true);
     * 
     * Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem,
     * Restaurante.class);
     * 
     * dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
     * Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
     * field.setAccessible(true);
     * 
     * Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
     * 
     * ReflectionUtils.setField(field, restauranteOrigem, novoValor);
     * });
     * } catch (IllegalArgumentException e) {
     * Throwable rootCause = ExceptionUtils.getRootCause(e);
     * throw new HttpMessageNotReadableException(e.getMessage(), rootCause,
     * serverHttpRequest);
     * }
     * 
     * }
     */
}
