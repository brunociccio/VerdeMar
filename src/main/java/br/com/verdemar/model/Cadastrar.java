package br.com.verdemar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_VMAR_CADASTRAR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cadastrar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome de usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "documentos_id")
    private Documentos documentos;
}

