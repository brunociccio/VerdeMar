package br.com.verdemar.assembler;

import br.com.verdemar.controller.LoginAppleController;
import br.com.verdemar.model.LoginApple;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoginAppleModelAssembler implements RepresentationModelAssembler<LoginApple, EntityModel<LoginApple>> {

    @Override
    public EntityModel<LoginApple> toModel(LoginApple loginApple) {
        return EntityModel.of(loginApple,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoginAppleController.class).show(loginApple.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoginAppleController.class).index(null)).withRel("login-apple"));
    }
}

