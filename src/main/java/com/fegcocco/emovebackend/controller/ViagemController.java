package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.AtualizarViagemDTO;
import com.fegcocco.emovebackend.dto.SalvarViagemDTO;
import com.fegcocco.emovebackend.entity.Viagens;
import com.fegcocco.emovebackend.service.TokenService;
import com.fegcocco.emovebackend.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viagens")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> salvarViagem(
            @CookieValue(name = "e-move-token") String token,
            @RequestBody SalvarViagemDTO dto) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Viagens viagemSalva = viagemService.salvarViagem(usuarioId, dto);
            return ResponseEntity.status(201).body(viagemSalva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarViagensDoUsuario(
            @CookieValue(name = "e-move-token") String token) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            List<Viagens> viagens = viagemService.listarViagensPorUsuario(usuarioId);
            return ResponseEntity.ok(viagens);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PatchMapping("/{viagemId}")
    public ResponseEntity<?> atualizarViagem(
            @CookieValue(name = "e-move-token") String token,
            @PathVariable Long viagemId,
            @RequestBody AtualizarViagemDTO dto) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Viagens viagemAtualizada = viagemService.atualizarFavorito(usuarioId, viagemId, dto);
            return ResponseEntity.ok(viagemAtualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}