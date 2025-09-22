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
    private Usuario id_usuario;

    @Column(nullable = true)
    private String nome_rota;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double longi;

    @Column(nullable = false)
    private float kmtotal;
}
