package com.fegcocco.emovebackend.dto;

import lombok.Data;

@Data
public class SalvarViagemDTO {
    private Long veiculoId;
    private Double kmTotal;
    private Double co2Preservado;

    //adicionar mais campos aqui como nomeDaRota, latOrigem/longiOrigem, latDestino/longDestino
}