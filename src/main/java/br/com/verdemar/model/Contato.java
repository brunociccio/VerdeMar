package br.com.verdemar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email alternativo é obrigatório")
    private String emailAlternativo;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Celular é obrigatório")
    private String celular;
}
