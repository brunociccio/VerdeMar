package br.com.verdemar.assembler;

import br.com.verdemar.controller.LoginController;
import br.com.verdemar.model.Login;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoginModelAssembler implements RepresentationModelAssembler<Login, EntityModel<Login>> {

    @Override
    public EntityModel<Login> toModel(Login login) {
        return EntityModel.of(login,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoginController.class).show(login.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LoginController.class).index(null)).withRel("logins"));
    }
}

