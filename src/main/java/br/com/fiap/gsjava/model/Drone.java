package br.com.fiap.gsjava.model;

import br.com.fiap.gsjava.model.enums.TipoDrone;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_gs_drone")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_drone", nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tp_drone", nullable = false)
    private TipoDrone tipo;

    @Column(name = "nr_capacidade_kg", nullable = false)
    private Double capacidadeKg;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Missao> missoes = new ArrayList<>();

    public Drone() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoDrone getTipo() {
        return tipo;
    }

    public void setTipo(TipoDrone tipo) {
        this.tipo = tipo;
    }

    public Double getCapacidadeKg() {
        return capacidadeKg;
    }

    public void setCapacidadeKg(Double capacidadeKg) {
        this.capacidadeKg = capacidadeKg;
    }

    public List<Missao> getMissoes() {
        return missoes;
    }
}
