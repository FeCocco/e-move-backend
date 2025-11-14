package com.fegcocco.emovebackend.dto;

import lombok.Data;

@Data
public class SalvarViagemDTO {
    private Long veiculoId;
    private Double kmTotal;
    private Double co2Preservado;

    private Double latOrigem;
    private Double longiOrigem;

    private Double latDestino;
    private Double longiDestino;

    boolean favorita;

    private String apelido;
}