package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.RelatorioPoluicaoModelAssembler;
import br.com.verdemar.model.RelatorioPoluicao;
import br.com.verdemar.repository.RelatorioPoluicaoRepository;
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
@RequestMapping("/relatorios-poluicao")
@Tag(name = "RelatorioPoluicao", description = "APIs relacionadas aos relatórios de poluição")
@Slf4j
public class RelatorioPoluicaoController {

    @Autowired
    private RelatorioPoluicaoRepository repository;

    @Autowired
    private RelatorioPoluicaoModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<RelatorioPoluicao> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os relatórios de poluição", description = "Retorna uma lista de todos os relatórios de poluição com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de relatórios de poluição retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<RelatorioPoluicao>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os relatórios de poluição com paginação");
        Page<RelatorioPoluicao> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar relatório de poluição por ID", description = "Retorna um relatório de poluição específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório de poluição retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Relatório de poluição não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<RelatorioPoluicao> show(@PathVariable Long id) {
        log.info("Buscando relatório de poluição com id {}", id);
        var relatorioPoluicao = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório de poluição não encontrado")
        );
        return assembler.toModel(relatorioPoluicao);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo relatório de poluição", description = "Cria um novo relatório de poluição com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Relatório de poluição criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<RelatorioPoluicao>> create(@RequestBody @Valid RelatorioPoluicao relatorioPoluicao) {
        log.info("Criando um novo relatório de poluição");
        repository.save(relatorioPoluicao);
        var entityModel = assembler.toModel(relatorioPoluicao);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um relatório de poluição", description = "Atualiza os dados de um relatório de poluição existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Relatório de poluição atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Relatório de poluição não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<RelatorioPoluicao>> update(@PathVariable Long id, @RequestBody @Valid RelatorioPoluicao relatorioPoluicao) {
        log.info("Atualizando relatório de poluição com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório de poluição não encontrado")
        );
        relatorioPoluicao.setId(id);
        repository.save(relatorioPoluicao);
        return ResponseEntity.ok(assembler.toModel(relatorioPoluicao));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um relatório de poluição", description = "Exclui um relatório de poluição existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Relatório de poluição excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Relatório de poluição não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo relatório de poluição com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Relatório de poluição não encontrado")
        );
        repository.deleteById(id);
    }
}

