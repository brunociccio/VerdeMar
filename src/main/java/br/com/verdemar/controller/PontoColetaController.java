package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.PontoColetaModelAssembler;
import br.com.verdemar.model.PontoColeta;
import br.com.verdemar.repository.PontoColetaRepository;
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
@RequestMapping("/pontos-coleta")
@Tag(name = "PontoColeta", description = "APIs relacionadas aos pontos de coleta")
@Slf4j
public class PontoColetaController {

    @Autowired
    private PontoColetaRepository repository;

    @Autowired
    private PontoColetaModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<PontoColeta> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os pontos de coleta", description = "Retorna uma lista de todos os pontos de coleta com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pontos de coleta retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<PontoColeta>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os pontos de coleta com paginação");
        Page<PontoColeta> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar ponto de coleta por ID", description = "Retorna um ponto de coleta específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ponto de coleta retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ponto de coleta não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<PontoColeta> show(@PathVariable Long id) {
        log.info("Buscando ponto de coleta com id {}", id);
        var pontoColeta = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Ponto de coleta não encontrado")
        );
        return assembler.toModel(pontoColeta);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo ponto de coleta", description = "Cria um novo ponto de coleta com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ponto de coleta criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<PontoColeta>> create(@RequestBody @Valid PontoColeta pontoColeta) {
        log.info("Criando um novo ponto de coleta");
        repository.save(pontoColeta);
        var entityModel = assembler.toModel(pontoColeta);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um ponto de coleta", description = "Atualiza os dados de um ponto de coleta existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ponto de coleta atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ponto de coleta não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<PontoColeta>> update(@PathVariable Long id, @RequestBody @Valid PontoColeta pontoColeta) {
        log.info("Atualizando ponto de coleta com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Ponto de coleta não encontrado")
        );
        pontoColeta.setId(id);
        repository.save(pontoColeta);
        return ResponseEntity.ok(assembler.toModel(pontoColeta));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um ponto de coleta", description = "Exclui um ponto de coleta existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ponto de coleta excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Ponto de coleta não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo ponto de coleta com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Ponto de coleta não encontrado")
        );
        repository.deleteById(id);
    }
}
