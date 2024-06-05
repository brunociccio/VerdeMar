package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.DocumentosModelAssembler;
import br.com.verdemar.model.Documentos;
import br.com.verdemar.repository.DocumentosRepository;
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
@RequestMapping("/documentos")
@Tag(name = "Documentos", description = "APIs relacionadas aos documentos")
@Slf4j
public class DocumentosController {

    @Autowired
    private DocumentosRepository repository;

    @Autowired
    private DocumentosModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Documentos> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os documentos", description = "Retorna uma lista de todos os documentos com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de documentos retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Documentos>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os documentos com paginação");
        Page<Documentos> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar documentos por ID", description = "Retorna um documento específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Documentos> show(@PathVariable Long id) {
        log.info("Buscando documentos com id {}", id);
        var documentos = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento não encontrado")
        );
        return assembler.toModel(documentos);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo documento", description = "Cria um novo documento com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Documento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Documentos>> create(@RequestBody @Valid Documentos documentos) {
        log.info("Criando um novo documento");
        repository.save(documentos);
        var entityModel = assembler.toModel(documentos);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um documento", description = "Atualiza os dados de um documento existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Documento atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Documentos>> update(@PathVariable Long id, @RequestBody @Valid Documentos documentos) {
        log.info("Atualizando documento com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento não encontrado")
        );
        documentos.setId(id);
        repository.save(documentos);
        return ResponseEntity.ok(assembler.toModel(documentos));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um documento", description = "Exclui um documento existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Documento excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Documento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo documento com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Documento não encontrado")
        );
        repository.deleteById(id);
    }
}

