package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USUARIOS_VEICULOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioVeiculo {

    @EmbeddedId
    private UsuarioVeiculoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    // insertable = false e updatable = false para resolver o conflito
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("veiculoId")
    // insertable = false e updatable = false para resolver o conflito
    @JoinColumn(name = "ID_VEICULO", insertable = false, updatable = false)
    private Veiculos veiculo;

    @Column(name = "nivel_bateria")
    private Integer nivelBateria;

    public UsuarioVeiculo(Usuario usuario, Veiculos veiculo, Integer nivelBateria) {
        this.id = new UsuarioVeiculoId(usuario.getIdUsuario(), veiculo.getId());
        this.usuario = usuario;
        this.veiculo = veiculo;
        this.nivelBateria = nivelBateria;
    }
}