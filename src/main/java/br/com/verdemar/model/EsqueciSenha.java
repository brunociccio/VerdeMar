package br.com.verdemar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class EsqueciSenha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O email é obrigatório")
    private String email;

    @NotBlank(message = "O código de recuperação é obrigatório")
    private String codigoRecuperacao;

    @NotBlank(message = "Informe a nova senha")
    private String novaSenha;
}

