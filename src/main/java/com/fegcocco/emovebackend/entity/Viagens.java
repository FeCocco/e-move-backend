package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "viagens")
@Getter
@Setter
public class Viagens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_viagem;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_veiculo", nullable = false)
    private  Veiculos veiculo;

    /*@ManyToOne
    @JoinColumn(name = "id_rota", nullable = false)
    private Rota rota;*/

    @Column(name = "km_total", nullable = false)
    private Double kmTotal;

    @Column(name = "co2_preservado", nullable = false)
    private Double co2Preservado;

    @Column(name = "dt_viagem", nullable = false)
    private LocalDate dtViagem;

}
