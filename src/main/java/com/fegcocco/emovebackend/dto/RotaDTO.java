package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Rotas;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RotaDTO {

    private Long id;
    private String nomeRota;
    private double lat;
    private double longi;
    private float kmTotal;

    public RotaDTO(Rotas rota) {
        this.id = rota.getId_rota();
        this.nomeRota = rota.getNome_rota();
        this.lat = rota.getLat();
        this.longi = rota.getLongi();
        this.kmTotal = rota.getKmTotal();
    }
}