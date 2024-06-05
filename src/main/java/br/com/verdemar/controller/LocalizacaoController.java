package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.LocalizacaoModelAssembler;
import br.com.verdemar.model.Localizacao;
import br.com.verdemar.repository.LocalizacaoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/localizacoes")
@Tag(name = "Localizacao", description = "APIs relacionadas às localizações")
@Slf4j
public class LocalizacaoController {

    @Autowired
    private LocalizacaoRepository repository;

    @Autowired
    private LocalizacaoModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Localizacao> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todas as localizações", description = "Retorna uma lista de todas as localizações com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de localizações retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Localizacao>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todas as localizações com paginação");
        Page<Localizacao> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar localização por ID", description = "Retorna uma localização específica pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Localização retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Localizacao> show(@PathVariable Long id) {
        log.info("Buscando localização com id {}", id);
        var localizacao = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Localização não encontrada")
        );
        return assembler.toModel(localizacao);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar uma nova localização", description = "Cria uma nova localização com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Localização criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Localizacao>> create(@RequestBody @Valid Localizacao localizacao) {
        log.info("Criando uma nova localização");
        repository.save(localizacao);
        var entityModel = assembler.toModel(localizacao);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma localização", description = "Atualiza os dados de uma localização existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Localização atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Localizacao>> update(@PathVariable Long id, @RequestBody @Valid Localizacao localizacao) {
        log.info("Atualizando localização com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Localização não encontrada")
        );
        localizacao.setId(id);
        repository.save(localizacao);
        return ResponseEntity.ok(assembler.toModel(localizacao));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir uma localização", description = "Exclui uma localização existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Localização excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Localização não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo localização com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Localização não encontrada")
        );
        repository.deleteById(id);
    }
}
