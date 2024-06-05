package br.com.verdemar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@Controller
@OpenAPIDefinition(
		info = @Info(
		title = "VerdeMar API",
		version = "1.0.0",
		description = "API do app VerdeMar - Economia Azul: projeto desenvolvido para a Global Solution que aborda a sustentabilidade e preservação dos Oceanos",
		contact = @Contact(name = "Bruno Ciccio", email = "dev.bruno.ciccio@gmail.com")
	)
)
public class VerdemarApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerdemarApplication.class, args);
	}

	@RequestMapping("/home")
    @ResponseBody
    public String home() {
        return "WebApp VerdeMar";
    }
}
