package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.ocm.StationDTO;
import com.fegcocco.emovebackend.service.OpenChargeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estacoes")
public class EstacaoController {

    @Autowired
    private OpenChargeMapService ocmService;

    @GetMapping("/proximas")
    public ResponseEntity<List<StationDTO>> buscarProximas(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "50") Double raio) {
        return ResponseEntity.ok(ocmService.buscarEstacoesProximas(lat, lon, raio));
    }
}