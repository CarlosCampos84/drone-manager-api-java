package br.com.fiap.gsjava.dto;

import br.com.fiap.gsjava.model.Drone;

public record DroneResponseDTO(
        Long id,
        String nome,
        String tipo,
        Double capacidadeKg
) {
    public static DroneResponseDTO fromDrone(Drone drone) {
        return new DroneResponseDTO(
                drone.getId(),
                drone.getNome(),
                drone.getTipo().name(),
                drone.getCapacidadeKg()
        );
    }
}
