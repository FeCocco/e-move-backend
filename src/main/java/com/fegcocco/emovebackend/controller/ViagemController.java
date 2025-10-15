package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.ViagemDTO;
import com.fegcocco.emovebackend.service.TokenService;
import com.fegcocco.emovebackend.service.ViagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/viagens")
public class ViagemController {

    @Autowired
    private ViagemService viagemService;

    @Autowired
    private TokenService tokenService;

    // listar as viagens do usuário logado
    @GetMapping("/me")
    public ResponseEntity<?> getMinhasViagens(@CookieValue(name = "e-move-token") String token) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Set<ViagemDTO> viagens = viagemService.listarViagensDoUsuario(usuarioId);
            return ResponseEntity.ok(viagens);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }
    }

    // salvar uma nova viagem
    @PostMapping
    public ResponseEntity<?> registrarViagem(
            @CookieValue(name = "e-move-token") String token,
            @RequestBody ViagemDTO viagemDTO) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            ViagemDTO viagemSalva = viagemService.registrarViagem(usuarioId, viagemDTO);
            return ResponseEntity.status(201).body(viagemSalva);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao registrar viagem: " + e.getMessage());
        }
    }
}