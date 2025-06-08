package br.com.fiap.gsjava.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank
                @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,
        @Email
        @NotBlank
        String email,
        @NotBlank
                @Size(min = 6, message = "Senha deve ter entre 6")
        String senha
) {
}
