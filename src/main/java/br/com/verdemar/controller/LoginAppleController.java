package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.LoginAppleModelAssembler;
import br.com.verdemar.model.LoginApple;
import br.com.verdemar.repository.LoginAppleRepository;
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
@RequestMapping("/login-apple")
@Tag(name = "LoginApple", description = "APIs relacionadas aos logins Apple")
@Slf4j
public class LoginAppleController {

    @Autowired
    private LoginAppleRepository repository;

    @Autowired
    private LoginAppleModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<LoginApple> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os logins Apple", description = "Retorna uma lista de todos os logins Apple com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de logins Apple retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<LoginApple>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os logins Apple com paginação");
        Page<LoginApple> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar login Apple por ID", description = "Retorna um login Apple específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login Apple retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login Apple não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<LoginApple> show(@PathVariable Long id) {
        log.info("Buscando login Apple com id {}", id);
        var loginApple = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login Apple não encontrado")
        );
        return assembler.toModel(loginApple);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo login Apple", description = "Cria um novo login Apple com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Login Apple criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<LoginApple>> create(@RequestBody @Valid LoginApple loginApple) {
        log.info("Criando um novo login Apple");
        repository.save(loginApple);
        var entityModel = assembler.toModel(loginApple);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um login Apple", description = "Atualiza os dados de um login Apple existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login Apple atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login Apple não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<LoginApple>> update(@PathVariable Long id, @RequestBody @Valid LoginApple loginApple) {
        log.info("Atualizando login Apple com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login Apple não encontrado")
        );
        loginApple.setId(id);
        repository.save(loginApple);
        return ResponseEntity.ok(assembler.toModel(loginApple));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um login Apple", description = "Exclui um login Apple existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Login Apple excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Login Apple não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo login Apple com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Login Apple não encontrado")
        );
        repository.deleteById(id);
    }
}
