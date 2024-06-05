package br.com.verdemar.assembler;

import br.com.verdemar.controller.EventosController;
import br.com.verdemar.model.Eventos;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventosModelAssembler implements RepresentationModelAssembler<Eventos, EntityModel<Eventos>> {

    @Override
    public EntityModel<Eventos> toModel(Eventos evento) {
        return EntityModel.of(evento,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventosController.class).show(evento.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EventosController.class).index(null)).withRel("eventos"));
    }
}
