package br.com.verdemar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
