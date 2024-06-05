package br.com.verdemar.assembler;

import br.com.verdemar.controller.ContatoController;
import br.com.verdemar.model.Contato;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ContatoModelAssembler implements RepresentationModelAssembler<Contato, EntityModel<Contato>>{
    @Override
    public EntityModel<Contato> toModel(Contato contato) {
        return EntityModel.of(contato,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ContatoController.class).show(contato.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ContatoController.class).index(null)).withRel("contato"));
    }
}
