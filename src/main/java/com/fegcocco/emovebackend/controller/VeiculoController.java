package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.VeiculoDTO;
import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.service.TokenService;
import com.fegcocco.emovebackend.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<Veiculos>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodosVeiculos());
    }

    @GetMapping("/meus-veiculos")
    public ResponseEntity<?> listarVeiculosDoUsuario(@CookieValue(name = "e-move-token") String token) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Set<VeiculoDTO> veiculos = veiculoService.listarVeiculosDoUsuario(usuarioId);
            return ResponseEntity.ok(veiculos);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado: " + e.getMessage());
        }
    }

    @PostMapping("/meus-veiculos/{veiculoId}")
    public ResponseEntity<?> adicionarVeiculo(
            @CookieValue(name = "e-move-token") String token,
            @PathVariable Long veiculoId) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Set<VeiculoDTO> veiculos = veiculoService.adicionarVeiculoParaUsuario(usuarioId, veiculoId);
            return ResponseEntity.ok(veiculos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/meus-veiculos/{veiculoId}")
    public ResponseEntity<?> removerVeiculo(
            @CookieValue(name = "e-move-token") String token,
            @PathVariable Long veiculoId) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Set<VeiculoDTO> veiculos = veiculoService.removerVeiculoDoUsuario(usuarioId, veiculoId);
            return ResponseEntity.ok(veiculos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}