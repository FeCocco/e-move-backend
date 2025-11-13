package com.fegcocco.emovebackend.dto.directions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationIQDirectionsResponseDTO {
    private List<LocationIQRouteDTO> routes;
    private String code;
}