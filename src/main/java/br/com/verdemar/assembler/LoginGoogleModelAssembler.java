package br.com.verdemar.assembler;

import br.com.verdemar.controller.LoginGoogleController;
import br.com.verdemar.model.LoginGoogle;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoginGoogleModelAssembler implements RepresentationModelAssembler<LoginGoogle, EntityModel<LoginGoogle>> {

    @Override
    public EntityModel<LoginGoogle> toModel(LoginGoogle loginGoogle) {
        return EntityModel.of(loginGoogle,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoginGoogleController.class).show(loginGoogle.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoginGoogleController.class).index(null)).withRel("login-google"));
    }
}
