package com.fegcocco.emovebackend.dto.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationIQRouteDTO {

    private double distance;

    private double duration;
    //linha da rota codificada (para desenhar no mapa)
    private String geometry;
}