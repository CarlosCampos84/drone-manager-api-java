package br.com.fiap.gsjava.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SuprimentoRequestDTO(
        @NotBlank
                @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String nome,
        @NotBlank
                @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
        String descricao,
        @Min(0)
        Double pesoKg
) {
}
