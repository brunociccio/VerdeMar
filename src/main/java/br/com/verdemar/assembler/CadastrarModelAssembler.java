package br.com.verdemar.assembler;

import br.com.verdemar.controller.CadastrarController;
import br.com.verdemar.model.Cadastrar;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class CadastrarModelAssembler implements RepresentationModelAssembler<Cadastrar, EntityModel<Cadastrar>> {

    @Override
    public EntityModel<Cadastrar> toModel(Cadastrar cadastrar) {
        return EntityModel.of(cadastrar,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastrarController.class).show(cadastrar.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CadastrarController.class).index(null)).withRel("cadastrar"));
    }
}

