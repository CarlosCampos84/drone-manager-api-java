package br.com.fiap.gsjava.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SuprimentoMissaoRequestDTO(
        @NotNull
        Long suprimentoId,
        @NotNull
                @Min(1)
        Integer quantidade
) {
}
