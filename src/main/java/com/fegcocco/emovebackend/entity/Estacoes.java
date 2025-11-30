package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estacoes")
@Getter
@Setter
@NoArgsConstructor
public class Estacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // pk do banco

    @Column(name = "station_id", nullable = false)
    private Long stationId; // ID do OpenChargeMap (chargepointid)

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Estacoes(Long stationId, Usuario usuario) {
        this.stationId = stationId;
        this.usuario = usuario;
    }
}