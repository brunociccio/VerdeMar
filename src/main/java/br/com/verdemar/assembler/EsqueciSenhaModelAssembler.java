package br.com.verdemar.assembler;

import br.com.verdemar.controller.EsqueciSenhaController;
import br.com.verdemar.model.EsqueciSenha;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class EsqueciSenhaModelAssembler implements RepresentationModelAssembler<EsqueciSenha, EntityModel<EsqueciSenha>> {

    @Override
    public EntityModel<EsqueciSenha> toModel(EsqueciSenha esqueciSenha) {
        return EntityModel.of(esqueciSenha,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EsqueciSenhaController.class).show(esqueciSenha.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EsqueciSenhaController.class).index(null)).withRel("esqueci-senha"));
    }
}
