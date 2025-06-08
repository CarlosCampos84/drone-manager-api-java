package br.com.fiap.gsjava.model;

public record DroneFilter(
        String tipo,
        Double capacidadeKgMin,
        Double capacidadeKgMax
) {
}
