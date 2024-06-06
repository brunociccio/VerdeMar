package br.com.verdemar.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_VMAR_LOGIN")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome de usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "Digite uma senha")
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    private LoginApple loginApple;

    @OneToOne(cascade = CascadeType.ALL)
    private LoginGoogle loginGoogle;

    @OneToOne(cascade = CascadeType.ALL)
    private EsqueciSenha esqueciSenha;

    @OneToOne(cascade = CascadeType.ALL)
    private Cadastrar cadastrar;
}

