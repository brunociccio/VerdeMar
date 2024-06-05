package br.com.verdemar.assembler;

import br.com.verdemar.controller.RelatorioPoluicaoController;
import br.com.verdemar.model.RelatorioPoluicao;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class RelatorioPoluicaoModelAssembler implements RepresentationModelAssembler<RelatorioPoluicao, EntityModel<RelatorioPoluicao>> {

    @Override
    public EntityModel<RelatorioPoluicao> toModel(RelatorioPoluicao relatorioPoluicao) {
        return EntityModel.of(relatorioPoluicao,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioPoluicaoController.class).show(relatorioPoluicao.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioPoluicaoController.class).index(null)).withRel("relatorios-poluicao"));
    }
}
