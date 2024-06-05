package br.com.verdemar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class RelatorioPoluicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Localizacao localizacao;

    @NotBlank(message = "Tipo de poluição é obrigatório")
    private String tipoPoluicao;

    @NotBlank(message = "Data é obrigatório")
    private LocalDateTime criadoEm;

}
