package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.ocm.StationDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
public class OpenChargeMapService {

    private final WebClient webClient;
    private final String apiKey;

    public OpenChargeMapService(WebClient.Builder webClientBuilder,
                                @Value("${openchargemap.api.url}") String apiUrl,
                                @Value("${openchargemap.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
        this.apiKey = apiKey;
    }

    public List<StationDTO> buscarEstacoesProximas(Double lat, Double lon, Double distanciaKm) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/poi/")
                        .queryParam("output", "json")
                        .queryParam("latitude", lat)
                        .queryParam("longitude", lon)
                        .queryParam("distance", distanciaKm)
                        .queryParam("distanceunit", "KM")
                        .queryParam("maxresults", 10)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToFlux(StationDTO.class)
                .collectList()
                .block();
    }
}