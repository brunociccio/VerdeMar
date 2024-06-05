package br.com.verdemar.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("VerdeMar API")
                                .version("1.0.0")
                                .description("API do app VerdeMar")
                                .contact(new Contact()
                                        .name("Bruno Ciccio")
                                        .email("dev.bruno.ciccio@gmail.com"))
                                .license(new License().name("Apache 2.0").url("http://springdoc.org")));
        }

        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("public")
                        .pathsToMatch("/cadastrar/**", "/cadastrarCnpj/**", "/contato/**", "/documentos/**", "/endereco/**",
                        "/esqueciSenha/**", "/eventos/**", "/localizacao/**", "/login/**", "/loginApple/**", "/loginGoogle/**",
                        "/pontoColeta/**", "/relatorio/**", "/usuario/**")
                        .build();
        }
}