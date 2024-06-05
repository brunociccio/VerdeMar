package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.LoginModelAssembler;
import br.com.verdemar.model.Login;
import br.com.verdemar.repository.LoginRepository;
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
@RequestMapping("/logins")
@Tag(name = "Login", description = "APIs relacionadas aos logins")
@Slf4j
public class LoginController {

    @Autowired
    private LoginRepository repository;

    @Autowired
    private LoginModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Login> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os logins", description = "Retorna uma lista de todos os logins com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de logins retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Login>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os logins com paginação");
        Page<Login> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar login por ID", description = "Retorna um login específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Login> show(@PathVariable Long id) {
        log.info("Buscando login com id {}", id);
        var login = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login não encontrado")
        );
        return assembler.toModel(login);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo login", description = "Cria um novo login com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Login criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Login>> create(@RequestBody @Valid Login login) {
        log.info("Criando um novo login");
        repository.save(login);
        var entityModel = assembler.toModel(login);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um login", description = "Atualiza os dados de um login existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Login>> update(@PathVariable Long id, @RequestBody @Valid Login login) {
        log.info("Atualizando login com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login não encontrado")
        );
        login.setId(id);
        repository.save(login);
        return ResponseEntity.ok(assembler.toModel(login));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um login", description = "Exclui um login existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Login excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo login com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login não encontrado")
        );
        repository.deleteById(id);
    }
}
