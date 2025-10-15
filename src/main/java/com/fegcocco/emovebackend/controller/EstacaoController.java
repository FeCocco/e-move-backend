package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.EstacaoDTO;
import com.fegcocco.emovebackend.service.EstacaoService;
import com.fegcocco.emovebackend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/estacoes")
public class EstacaoController {

    @Autowired
    private EstacaoService estacaoService;

    @Autowired
    private TokenService tokenService;

    // listar as estações favoritas do usuário logado
    @GetMapping("/me")
    public ResponseEntity<?> getMinhasEstacoes(@CookieValue(name = "e-move-token") String token) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Set<EstacaoDTO> estacoes = estacaoService.listarEstacoesDoUsuario(usuarioId);
            return ResponseEntity.ok(estacoes);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }
    }

    // salvart uma nova estação favorita
    @PostMapping
    public ResponseEntity<?> salvarEstacao(@CookieValue(name = "e-move-token") String token) {
        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            EstacaoDTO estacaoSalva = estacaoService.salvarEstacaoFavorita(usuarioId);
            return ResponseEntity.status(201).body(estacaoSalva);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao salvar estação: " + e.getMessage());
        }
    }
}