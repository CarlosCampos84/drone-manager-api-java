package br.com.fiap.gsjava.dto;

import br.com.fiap.gsjava.model.Missao;
import br.com.fiap.gsjava.model.enums.StatusMissao;

import java.time.LocalDateTime;
import java.util.List;

public record MissaoResponseDTO(
        Long id,
        String descricao,
        StatusMissao status,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        DroneResponseDTO drone,
        List<SuprimentoMissaoResponseDTO> suprimentos,
        Double pesoTotal
) {
    public static MissaoResponseDTO fromMissao(Missao missao) {
        List<SuprimentoMissaoResponseDTO> suprimentos = missao.getSuprimentos().stream()
                .map(SuprimentoMissaoResponseDTO::fromSuprimentoMissao)
                .toList();

        return new MissaoResponseDTO(
                missao.getId(),
                missao.getDescricao(),
                missao.getStatus(),
                missao.getDataInicio(),
                missao.getDataFim(),
                DroneResponseDTO.fromDrone(missao.getDrone()),
                suprimentos,
                missao.getPesoTotal()
        );
    }
}
