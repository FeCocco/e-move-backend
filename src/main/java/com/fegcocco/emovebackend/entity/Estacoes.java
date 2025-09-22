package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estacoes")
public class Estacoes {

    @Id
    private Long id_estacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario id_usuario;
}
