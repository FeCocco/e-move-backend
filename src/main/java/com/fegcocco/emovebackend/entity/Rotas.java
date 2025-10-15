package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rotas")
@Getter
@Setter
public class Rotas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rota;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nome_rota")
    private String nome_rota;

    @Column(nullable = false)
    private double lat;

    @Column(name = "longi", nullable = false)
    private double longi;

    @Column(name = "km_total", nullable = false)
    private float kmTotal;
}
