package br.com.verdemar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "TB_VMAR_EVENTOS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Eventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotBlank(message = "Data é obrigatória")
    private LocalDateTime dataHora;

    @OneToOne(cascade = CascadeType.ALL)
    private Localizacao localizacao;

    @ManyToOne
    private Usuario organizador;

    @ManyToMany
    private List<Usuario> participantes;

    @OneToOne(cascade = CascadeType.ALL)
    private PontoColeta pontoColeta;

}

