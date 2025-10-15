package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Viagens;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ViagemDTO {

    private Long id;
    private Long veiculoId;
    private Long rotaId;
    private Double kmTotal;
    private Double co2Preservado;
    private LocalDate dtViagem;

    public ViagemDTO(Viagens viagem) {
        this.id = viagem.getId();
        this.veiculoId = viagem.getVeiculo().getId();
        this.rotaId = viagem.getRota().getId_rota();
        this.kmTotal = viagem.getKmTotal();
        this.co2Preservado = viagem.getCo2Preservado();
        this.dtViagem = viagem.getDtViagem();
    }
}