package br.com.fiap.gsjava.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_gs_suprimento")
public class Suprimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_suprimento", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_suprimento", nullable = false, length = 500)
    private String descricao;

    @Column(name = "peso_kg", nullable = false)
    private Double pesoKg;

    @OneToMany(mappedBy = "suprimento")
    private List<SuprimentoMissao> missoes = new ArrayList<>();

    public Suprimento() {
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }
}
