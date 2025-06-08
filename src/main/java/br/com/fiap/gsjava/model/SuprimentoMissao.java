package br.com.fiap.gsjava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "t_gs_suprimento_missao")
public class SuprimentoMissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Missao missao;

    @ManyToOne
    private Suprimento suprimento;

    @Column(name = "qt_suprimento", nullable = false)
    private Integer quantidade;

    public SuprimentoMissao() {
    }

    public SuprimentoMissao(Suprimento suprimento, Missao missao, Integer quantidade) {
        this.suprimento = suprimento;
        this.missao = missao;
        this.quantidade = quantidade;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public Suprimento getSuprimento() {
        return suprimento;
    }

    public void setSuprimento(Suprimento suprimento) {
        this.suprimento = suprimento;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
