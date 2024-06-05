package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.LoginGoogleModelAssembler;
import br.com.verdemar.model.LoginGoogle;
import br.com.verdemar.repository.LoginGoogleRepository;
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
@RequestMapping("/login-google")
@Tag(name = "LoginGoogle", description = "APIs relacionadas aos logins Google")
@Slf4j
public class LoginGoogleController {

    @Autowired
    private LoginGoogleRepository repository;

    @Autowired
    private LoginGoogleModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<LoginGoogle> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os logins Google", description = "Retorna uma lista de todos os logins Google com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de logins Google retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<LoginGoogle>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os logins Google com paginação");
        Page<LoginGoogle> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar login Google por ID", description = "Retorna um login Google específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login Google retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login Google não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<LoginGoogle> show(@PathVariable Long id) {
        log.info("Buscando login Google com id {}", id);
        var loginGoogle = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login Google não encontrado")
        );
        return assembler.toModel(loginGoogle);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo login Google", description = "Cria um novo login Google com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Login Google criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<LoginGoogle>> create(@RequestBody @Valid LoginGoogle loginGoogle) {
        log.info("Criando um novo login Google");
        repository.save(loginGoogle);
        var entityModel = assembler.toModel(loginGoogle);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um login Google", description = "Atualiza os dados de um login Google existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login Google atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login Google não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<LoginGoogle>> update(@PathVariable Long id, @RequestBody @Valid LoginGoogle loginGoogle) {
        log.info("Atualizando login Google com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login Google não encontrado")
        );
        loginGoogle.setId(id);
        repository.save(loginGoogle);
        return ResponseEntity.ok(assembler.toModel(loginGoogle));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um login Google", description = "Exclui um login Google existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Login Google excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login Google não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo login Google com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login Google não encontrado")
        );
        repository.deleteById(id);
    }
}
