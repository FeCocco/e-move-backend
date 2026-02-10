package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.AtualizarNivelBateriaDTO;
import com.fegcocco.emovebackend.dto.VeiculoDTO;
import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.service.TokenService;
import com.fegcocco.emovebackend.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ResponseEntity<List<Veiculos>> listarTodos() {
        return ResponseEntity.ok(veiculoService.getAllVeiculos());
    }

    @GetMapping("/meus-veiculos")
    public ResponseEntity<?> listarVeiculosDoUsuario(HttpServletRequest request) {
        String token = tokenService.resolveToken(request);
        if (token == null) return ResponseEntity.status(401).body("Token não encontrado.");

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
            HttpServletRequest request,
            @PathVariable Long veiculoId) {

        String token = tokenService.resolveToken(request);
        if (token == null) return ResponseEntity.status(401).body("Token não encontrado.");

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
            HttpServletRequest request,
            @PathVariable Long veiculoId) {

        String token = tokenService.resolveToken(request);
        if (token == null) return ResponseEntity.status(401).body("Token não encontrado.");

        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Set<VeiculoDTO> veiculos = veiculoService.removerVeiculoDoUsuario(usuarioId, veiculoId);
            return ResponseEntity.ok(veiculos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{veiculoId}/bateria")
    public ResponseEntity<?> atualizarNivelBateria(
            HttpServletRequest request,
            @PathVariable Long veiculoId,
            @RequestBody AtualizarNivelBateriaDTO dto) {

        String token = tokenService.resolveToken(request);
        if (token == null) return ResponseEntity.status(401).body("Token não encontrado.");

        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            VeiculoDTO veiculoAtualizado = veiculoService.atualizarNivelBateria(usuarioId, veiculoId, dto.getNivelBateria());
            return ResponseEntity.ok(veiculoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}