package br.com.fiap.gsjava.dto;

import br.com.fiap.gsjava.model.Suprimento;

public record SuprimentoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double pesoKg
) {
    public static SuprimentoResponseDTO fromSuprimento(Suprimento suprimento) {
        return new SuprimentoResponseDTO(
                suprimento.getId(),
                suprimento.getNome(),
                suprimento.getDescricao(),
                suprimento.getPesoKg()
        );
    }
}
