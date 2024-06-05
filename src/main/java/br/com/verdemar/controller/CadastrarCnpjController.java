package br.com.verdemar.controller;

import static org.springframework.http.HttpStatus.*;

import br.com.verdemar.assembler.CadastrarCnpjModelAssembler;
import br.com.verdemar.model.CadastrarCnpj;
import br.com.verdemar.repository.CadastrarCnpjRepository;
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
@RequestMapping("/cadastrarCnpj")
@Tag(name = "CadastrarCnpj", description = "APIs relacionadas ao cadastro de CNPJs")
@Slf4j
public class CadastrarCnpjController {

    @Autowired
    private CadastrarCnpjRepository repository;

    @Autowired
    private CadastrarCnpjModelAssembler assembler;

    @Autowired
    private PagedResourcesAssembler<CadastrarCnpj> pagedAssembler;

    @GetMapping
    @Operation(summary = "Listar todos os CNPJs", description = "Retorna uma lista de todos os CNPJs com paginação")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de CNPJs retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public PagedModel<EntityModel<CadastrarCnpj>> index(@ParameterObject Pageable pageable) {
        log.info("Listando todos os CNPJs com paginação");
        Page<CadastrarCnpj> page = repository.findAll(pageable);
        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar CNPJ por ID", description = "Retorna um CNPJ específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CNPJ retornado com sucesso"),
        @ApiResponse(responseCode = "404", description = "CNPJ não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public EntityModel<CadastrarCnpj> show(@PathVariable Long id) {
        log.info("Buscando CNPJ com id {}", id);
        var cadastrarCnpj = repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("CNPJ não encontrado")
        );
        return assembler.toModel(cadastrarCnpj);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Criar um novo CNPJ", description = "Cria um novo CNPJ com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "CNPJ criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<CadastrarCnpj>> create(@RequestBody @Valid CadastrarCnpj cadastrarCnpj) {
        log.info("Criando um novo CNPJ");
        repository.save(cadastrarCnpj);
        var entityModel = assembler.toModel(cadastrarCnpj);
        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar um CNPJ", description = "Atualiza os dados de um CNPJ existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "CNPJ atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "CNPJ não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<EntityModel<CadastrarCnpj>> update(@PathVariable Long id, @RequestBody @Valid CadastrarCnpj cadastrarCnpj) {
        log.info("Atualizando CNPJ com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("CNPJ não encontrado")
        );
        cadastrarCnpj.setId(id);
        repository.save(cadastrarCnpj);
        return ResponseEntity.ok(assembler.toModel(cadastrarCnpj));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Excluir um CNPJ", description = "Exclui um CNPJ existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "CNPJ excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "CNPJ não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public void destroy(@PathVariable Long id) {
        log.info("Excluindo CNPJ com id {}", id);
        repository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("CNPJ não encontrado")
        );
        repository.deleteById(id);
    }
}
