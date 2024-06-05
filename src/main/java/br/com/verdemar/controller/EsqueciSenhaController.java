package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.EsqueciSenhaModelAssembler;
import br.com.verdemar.model.EsqueciSenha;
import br.com.verdemar.repository.EsqueciSenhaRepository;
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
@RequestMapping("/esqueci-senha")
@Tag(name = "EsqueciSenha", description = "APIs relacionadas à recuperação de senha")
@Slf4j
public class EsqueciSenhaController {

    @Autowired
    private EsqueciSenhaRepository repository;

    @Autowired
    private EsqueciSenhaModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<EsqueciSenha> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todas as solicitações de recuperação de senha", description = "Retorna uma lista de todas as solicitações de recuperação de senha com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de solicitações retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<EsqueciSenha>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todas as solicitações de recuperação de senha com paginação");
        Page<EsqueciSenha> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar solicitação de recuperação de senha por ID", description = "Retorna uma solicitação específica pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitação retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<EsqueciSenha> show(@PathVariable Long id) {
        log.info("Buscando solicitação de recuperação de senha com id {}", id);
        var esqueciSenha = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Solicitação não encontrada")
        );
        return assembler.toModel(esqueciSenha);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar uma nova solicitação de recuperação de senha", description = "Cria uma nova solicitação de recuperação de senha com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Solicitação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<EsqueciSenha>> create(@RequestBody @Valid EsqueciSenha esqueciSenha) {
        log.info("Criando uma nova solicitação de recuperação de senha");
        repository.save(esqueciSenha);
        var entityModel = assembler.toModel(esqueciSenha);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar uma solicitação de recuperação de senha", description = "Atualiza os dados de uma solicitação de recuperação de senha existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitação atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<EsqueciSenha>> update(@PathVariable Long id, @RequestBody @Valid EsqueciSenha esqueciSenha) {
        log.info("Atualizando solicitação de recuperação de senha com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Solicitação não encontrada")
        );
        esqueciSenha.setId(id);
        repository.save(esqueciSenha);
        return ResponseEntity.ok(assembler.toModel(esqueciSenha));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir uma solicitação de recuperação de senha", description = "Exclui uma solicitação de recuperação de senha existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Solicitação excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Solicitação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo solicitação de recuperação de senha com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Solicitação não encontrada")
        );
        repository.deleteById(id);
    }
}

