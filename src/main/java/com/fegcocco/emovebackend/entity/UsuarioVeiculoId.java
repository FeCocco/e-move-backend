package com.fegcocco.emovebackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UsuarioVeiculoId implements Serializable {

    @Column(name = "ID_USUARIO")
    private Long usuarioId;

    @Column(name = "ID_VEICULO")
    private Long veiculoId;
}