package br.com.fiap.gsjava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record MissaoRequestDTO(
        @NotBlank
        String descricao,
        @NotNull
        Long droneId,
        @NotNull
        Set<SuprimentoMissaoRequestDTO> suprimentos
) {
}
