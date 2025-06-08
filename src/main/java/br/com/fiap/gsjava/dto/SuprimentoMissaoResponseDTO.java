package br.com.fiap.gsjava.dto;

import br.com.fiap.gsjava.model.SuprimentoMissao;

public record SuprimentoMissaoResponseDTO(
        SuprimentoResponseDTO suprimento,
        Integer quantidade
) {
    public static SuprimentoMissaoResponseDTO fromSuprimentoMissao(SuprimentoMissao suprimentoMissao) {
        return new SuprimentoMissaoResponseDTO(
                SuprimentoResponseDTO.fromSuprimento(suprimentoMissao.getSuprimento()),
                suprimentoMissao.getQuantidade()
        );
    }
}
