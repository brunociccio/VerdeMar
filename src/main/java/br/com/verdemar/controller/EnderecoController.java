package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.EnderecoModelAssembler;
import br.com.verdemar.model.Endereco;
import br.com.verdemar.repository.EnderecoRepository;
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
@RequestMapping("/endereco")
@Tag(name = "Endereço", description = "APIs relacionadas ao Endereço")
@Slf4j
public class EnderecoController {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private EnderecoModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Endereco> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os endereços", description = "Retorna uma lista de todos os endereços com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Endereco>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os endereços com paginação");
        Page<Endereco> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar endereço por ID", description = "Retorna um endereço específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Endereço retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Endereco> show(@PathVariable Long id) {
        log.info("Buscando endereço com id {}", id);
        var endereco = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Endereço não encontrado")
        );
        return assembler.toModel(endereco);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo endereço", description = "Cria um novo endereço com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Endereco>> create(@RequestBody @Valid Endereco endereco) {
        log.info("Criando um novo endereço");
        repository.save(endereco);
        var entityModel = assembler.toModel(endereco);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um endereço", description = "Atualiza os dados de um endereço existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Endereco>> update(@PathVariable Long id, @RequestBody @Valid Endereco endereco) {
        log.info("Atualizando endereço com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Endereço não encontrado")
        );
        endereco.setId(id);
        repository.save(endereco);
        return ResponseEntity.ok(assembler.toModel(endereco));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um endereço", description = "Exclui um endereço existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo endereço com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Endereço não encontrado")
        );
        repository.deleteById(id);
    }
}
