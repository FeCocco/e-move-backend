package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
public class Veiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_PLUGIN", nullable = false, length = 20)
    private TipoPlugin tipoPlugin;

    public enum TipoPlugin {
        SAE_TIPO_1,
        TIPO_2_AC,
        CCS_TIPO_2,
        CHADEMO,
        GBT
    }

    @Column(nullable = false)
    private int autonomia;
}
