package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.GeocodingDTO;
// IMPORTS ADICIONADOS
import com.fegcocco.emovebackend.dto.directions.LocationIQDirectionsResponseDTO;
import java.util.Locale;
// FIM DOS IMPORTS ADICIONADOS
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${locationiq.api.key}")
    private String apiKey;

    @Value("${locationiq.api.url}")
    private String apiUrl;

    @Value("${locationiq.api.directions.url}")
    private String directionsApiUrl;

    public GeocodingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public List<GeocodingDTO> forwardGeocode(String address) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("key", apiKey)
                    .queryParam("q", address)
                    .queryParam("format", "json")
                    .queryParam("limit", "5")
                    .build()
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);

            List<GeocodingDTO> results = new ArrayList<>();
            if (root.isArray()) {
                for (JsonNode node : root) {
                    GeocodingDTO dto = new GeocodingDTO();
                    dto.setAddress(address);
                    dto.setLatitude(node.get("lat").asDouble());
                    dto.setLongitude(node.get("lon").asDouble());
                    dto.setDisplayName(node.get("display_name").asText());
                    results.add(dto);
                }
            }

            return results;
        } catch (Exception e) {
            throw new RuntimeException("Error during forward geocoding: " + e.getMessage(), e);
        }
    }


    public LocationIQDirectionsResponseDTO getDirectRoute(double oLat, double oLon, double dLat, double dLon) {
        try {
            // garante que o separador decimal seja um ponto "."
            String coordinates = String.format(Locale.US, "%.6f,%.6f;%.6f,%.6f", oLon, oLat, dLon, dLat);

            String url = UriComponentsBuilder.fromHttpUrl(directionsApiUrl + coordinates)
                    .queryParam("key", apiKey)
                    .queryParam("geometries", "polyline") // Pede a geometria para desenhar no mapa
                    .queryParam("overview", "full")       // Garante que a geometria seja da rota inteira
                    .queryParam("steps", "false")         //MUDAR NA IMPLEMENTACAO DA OPENCHARGEMAP
                    .build()
                    .toUriString();

            LocationIQDirectionsResponseDTO response = restTemplate.getForObject(url, LocationIQDirectionsResponseDTO.class);

            if (response == null || !"Ok".equalsIgnoreCase(response.getCode())) {
                throw new RuntimeException("API de Rotas não retornou 'Ok'. Resposta: " + (response != null ? response.getCode() : "nula"));
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular a rota: " + e.getMessage(), e);
        }
    }
}