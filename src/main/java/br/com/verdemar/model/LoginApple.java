package br.com.verdemar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "TB_VMAR_LOGIN_APPLE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginApple {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Apple ID é obrigatório")
    private String appleId;

    @NotBlank(message = "Token é obrigatório")
    private String token;

}
