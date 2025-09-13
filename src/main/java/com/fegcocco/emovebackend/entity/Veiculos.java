package com.fegcocco.emovebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
@NoArgsConstructor
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

    @ManyToMany(mappedBy = "veiculos")
    @JsonIgnore // Evita problemas de serialização (loop infinito)
    private Set<Usuario> usuarios = new HashSet<>();

    public Veiculos(String marca, String modelo, int autonomia, TipoPlugin tipoPlugin) {
        this.marca = marca;
        this.modelo = modelo;
        this.autonomia = autonomia;
        this.tipoPlugin = tipoPlugin;
    }
}
