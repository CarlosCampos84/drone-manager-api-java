package br.com.fiap.gsjava.model;

import br.com.fiap.gsjava.model.enums.StatusMissao;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_gs_missao")
public class Missao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ds_missao", nullable = false, length = 500)
    private String descricao;

    @Column(name = "dt_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "dt_fim")
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_missao", nullable = false)
    private StatusMissao status;

    @Column(name = "nr_peso_total", nullable = false)
    private Double pesoTotal;

    @ManyToOne
    private Drone drone;

    @OneToMany(mappedBy = "missao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuprimentoMissao> suprimentos = new ArrayList<>();

    public Missao() {
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public StatusMissao getStatus() {
        return status;
    }

    public void setStatus(StatusMissao status) {
        this.status = status;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public List<SuprimentoMissao> getSuprimentos() {
        return suprimentos;
    }

    public void setSuprimentos(List<SuprimentoMissao> suprimentos) {
        this.suprimentos = suprimentos;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        if (pesoTotal > drone.getCapacidadeKg())
            throw new IllegalArgumentException("Peso total dos suprimentos excede a capacidade do drone");
        this.pesoTotal = pesoTotal;
    }
}
