package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.EventosModelAssembler;
import br.com.verdemar.model.Eventos;
import br.com.verdemar.repository.EventosRepository;
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
@RequestMapping("/eventos")
@Tag(name = "Eventos", description = "APIs relacionadas aos eventos")
@Slf4j
public class EventosController {

    @Autowired
    private EventosRepository repository;

    @Autowired
    private EventosModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Eventos> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os eventos", description = "Retorna uma lista de todos os eventos com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Eventos>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os eventos com paginação");
        Page<Eventos> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar evento por ID", description = "Retorna um evento específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Eventos> show(@PathVariable Long id) {
        log.info("Buscando evento com id {}", id);
        var evento = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Evento não encontrado")
        );
        return assembler.toModel(evento);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo evento", description = "Cria um novo evento com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Eventos>> create(@RequestBody @Valid Eventos evento) {
        log.info("Criando um novo evento");
        repository.save(evento);
        var entityModel = assembler.toModel(evento);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um evento", description = "Atualiza os dados de um evento existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Eventos>> update(@PathVariable Long id, @RequestBody @Valid Eventos evento) {
        log.info("Atualizando evento com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Evento não encontrado")
        );
        evento.setId(id);
        repository.save(evento);
        return ResponseEntity.ok(assembler.toModel(evento));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um evento", description = "Exclui um evento existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Evento excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo evento com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Evento não encontrado")
        );
        repository.deleteById(id);
    }
}

