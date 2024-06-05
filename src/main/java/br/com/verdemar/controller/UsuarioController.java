package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.UsuarioModelAssembler;
import br.com.verdemar.model.Usuario;
import br.com.verdemar.repository.UsuarioRepository;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "APIs relacionadas aos usuários")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<Usuario> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<Usuario>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os usuários com paginação");
        Page<Usuario> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<Usuario> show(@PathVariable Long id) {
        log.info("Buscando usuário com id {}", id);
        var usuario = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Usuário não encontrado")
        );
        return assembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Usuario>> create(@RequestBody @Valid Usuario usuario) {
        log.info("Criando um novo usuário");
        repository.save(usuario);
        var entityModel = assembler.toModel(usuario);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<Usuario>> update(@PathVariable Long id, @RequestBody @Valid Usuario usuario) {
        log.info("Atualizando usuário com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Usuário não encontrado")
        );
        usuario.setId(id);
        repository.save(usuario);
        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um usuário", description = "Exclui um usuário existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo usuário com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Usuário não encontrado")
        );
        repository.deleteById(id);
    }
}

