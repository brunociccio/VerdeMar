package br.com.verdemar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    private String referencia;
    private String descricao;
    private String urlFoto;

    @ManyToOne
    private Usuario reportadoPor;

    @NotBlank(message = "Data é obrigatório")
    private LocalDateTime reportadoEm;
}
