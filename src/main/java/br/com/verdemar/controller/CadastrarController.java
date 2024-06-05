package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.CadastrarModelAssembler;
import br.com.verdemar.model.Cadastrar;
import br.com.verdemar.repository.CadastrarRepository;
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
@RequestMapping("/cadastrar")
@Tag(name = "Cadastrar", description = "APIs relacionadas ao cadastro de usuários")
@Slf4j
public class CadastrarController {

    @Autowired
    private CadastrarRepository repository;

    @Autowired
    private CadastrarModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Cadastrar> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os cadastros", description = "Retorna uma lista de todos os cadastros com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de cadastros retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Cadastrar>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os cadastros com paginação");
        Page<Cadastrar> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar cadastro por ID", description = "Retorna um cadastro específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cadastro retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Cadastrar> show(@PathVariable Long id) {
        log.info("Buscando cadastro com id {}", id);
        var cadastrar = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Cadastro não encontrado")
        );
        return assembler.toModel(cadastrar);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo cadastro", description = "Cria um novo cadastro com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cadastro criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Cadastrar>> create(@RequestBody @Valid Cadastrar cadastrar) {
        log.info("Criando um novo cadastro");
        repository.save(cadastrar);
        var entityModel = assembler.toModel(cadastrar);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um cadastro", description = "Atualiza os dados de um cadastro existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cadastro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Cadastrar>> update(@PathVariable Long id, @RequestBody @Valid Cadastrar cadastrar) {
        log.info("Atualizando cadastro com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Cadastro não encontrado")
        );
        cadastrar.setId(id);
        repository.save(cadastrar);
        return ResponseEntity.ok(assembler.toModel(cadastrar));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um cadastro", description = "Exclui um cadastro existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cadastro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cadastro não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo cadastro com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Cadastro não encontrado")
        );
        repository.deleteById(id);
    }
}
