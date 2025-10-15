package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.RotaDTO;
import com.fegcocco.emovebackend.service.RotaService;
import com.fegcocco.emovebackend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/rotas")
public class RotaController {

    @Autowired
    private RotaService rotaService;

    @Autowired
    private TokenService tokenService;

    // lisatr as rotas favoritas do usuario logado
    @GetMapping("/me")
    public ResponseEntity<?> getMinhasRotas(@CookieValue(name = "e-move-token") String token) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Set<RotaDTO> rotas = rotaService.listarRotasDoUsuario(usuarioId);
            return ResponseEntity.ok(rotas);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }
    }

    // salvar uma nova rota favorita
    @PostMapping
    public ResponseEntity<?> salvarRota(
            @CookieValue(name = "e-move-token") String token,
            @RequestBody RotaDTO rotaDTO) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            RotaDTO rotaSalva = rotaService.salvarRotaFavorita(usuarioId, rotaDTO);
            return ResponseEntity.status(201).body(rotaSalva); // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao salvar rota: " + e.getMessage());
        }
    }
}