package br.com.verdemar.assembler;

import br.com.verdemar.controller.LocalizacaoController;
import br.com.verdemar.model.Localizacao;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class LocalizacaoModelAssembler implements RepresentationModelAssembler<Localizacao, EntityModel<Localizacao>> {

    @Override
    public EntityModel<Localizacao> toModel(Localizacao localizacao) {
        return EntityModel.of(localizacao,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LocalizacaoController.class).show(localizacao.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LocalizacaoController.class).index(null)).withRel("localizacoes"));
    }
}

