package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.GeocodingDTO;
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
}