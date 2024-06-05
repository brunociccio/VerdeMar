package br.com.verdemar.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import br.com.verdemar.controller.DocumentosController;
import br.com.verdemar.model.Documentos;

@Component
public class DocumentosModelAssembler implements RepresentationModelAssembler<Documentos, EntityModel<Documentos>>{
    @Override
    public EntityModel<Documentos> toModel(Documentos documentos) {
        return EntityModel.of(documentos,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosController.class).show(documentos.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocumentosController.class).index(null)).withRel("documentos"));
    }
}
