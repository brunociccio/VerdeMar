package br.com.verdemar.assembler;

import br.com.verdemar.controller.PontoColetaController;
import br.com.verdemar.model.PontoColeta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class PontoColetaModelAssembler implements RepresentationModelAssembler<PontoColeta, EntityModel<PontoColeta>> {

    @Override
    public EntityModel<PontoColeta> toModel(PontoColeta pontoColeta) {
        return EntityModel.of(pontoColeta,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PontoColetaController.class).show(pontoColeta.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PontoColetaController.class).index(null)).withRel("pontos-coleta"));
    }
}
