package com.fegcocco.emovebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoDTO {

    private Long id;
    private String marca;
    private String modelo;
    private Integer nivelBateria;
    private int autonomiaTotal;
    private double autonomiaEstimada;

}