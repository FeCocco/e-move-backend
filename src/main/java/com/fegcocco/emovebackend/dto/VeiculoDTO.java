package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Veiculos;
import lombok.Getter;

@Getter
public class VeiculoDTO {

    private final Long id;
    private final String marca;
    private final String modelo;
    private final int autonomia;
    private final Veiculos.TipoPlugin tipoPlugin;

    public VeiculoDTO(Veiculos veiculo) {
        this.id = veiculo.getId();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.autonomia = veiculo.getAutonomia();
        this.tipoPlugin = veiculo.getTipoPlugin();
    }
}