package br.com.fiap.gsjava.dto;

import br.com.fiap.gsjava.model.enums.TipoDrone;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DroneRequestDTO(
        @NotBlank
        String nome,
        @NotNull
        TipoDrone tipo,
        @NotNull
        @Min(0)
        Double capacidadeKg
) {
}
