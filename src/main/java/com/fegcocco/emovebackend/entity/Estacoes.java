package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estacoes")
@Getter
@Setter
public class Estacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestacao")
    private Long id;

    // A coluna 'id_estacao' que representa o ID da estação vindo de uma API externa sera adicionada em uma etapa futura, por enquanto, a entidade apenas cria o vínculo.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}