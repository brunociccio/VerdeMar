package br.com.verdemar.assembler;

import br.com.verdemar.controller.CadastrarCnpjController;
import br.com.verdemar.model.CadastrarCnpj;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CadastrarCnpjModelAssembler implements RepresentationModelAssembler<CadastrarCnpj, EntityModel<CadastrarCnpj>> {

    @Override
    public EntityModel<CadastrarCnpj> toModel(CadastrarCnpj cadastrarCnpj) {
        return EntityModel.of(cadastrarCnpj,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastrarCnpjController.class).show(cadastrarCnpj.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastrarCnpjController.class).index(null)).withRel("cadastrarCnpjs"));
    }
}

