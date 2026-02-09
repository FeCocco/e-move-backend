package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.GeocodingDTO;
import com.fegcocco.emovebackend.dto.directions.LocationIQDirectionsResponseDTO;
import com.fegcocco.emovebackend.service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class MapaController {

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping("/forward-geocoding")
    public ResponseEntity<List<GeocodingDTO>> forwardGeocode(@RequestParam String address) {
        try {
            List<GeocodingDTO> results = geocodingService.forwardGeocode(address);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/directions")
    public ResponseEntity<?> getDirectRoute(
            @RequestParam double oLat, @RequestParam double oLon, // Origem
            @RequestParam double dLat, @RequestParam double dLon  // Destino
    ) {
        try {
            LocationIQDirectionsResponseDTO results = geocodingService.getDirectRoute(oLat, oLon, dLat, dLon);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}