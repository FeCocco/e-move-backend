package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Veiculos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoDTO {

    private Long id;
    private String marca;
    private String modelo;
    private int autonomia;
    private Veiculos.TipoPlugin tipoPlugin;

    // Construtor que converte a Entidade em DTO
    public VeiculoDTO(Veiculos veiculo) {
        this.id = veiculo.getId();
        this.marca = veiculo.getMarca();
        this.modelo = veiculo.getModelo();
        this.autonomia = veiculo.getAutonomia();
        this.tipoPlugin = veiculo.getTipoPlugin();
    }
}