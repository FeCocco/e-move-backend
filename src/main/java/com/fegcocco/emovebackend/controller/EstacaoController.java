package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.ocm.StationDTO;
import com.fegcocco.emovebackend.service.OpenChargeMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fegcocco.emovebackend.service.TokenService;
import com.fegcocco.emovebackend.entity.Estacoes;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.EstacaoRepository;
import com.fegcocco.emovebackend.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estacoes")
public class EstacaoController {

    @Autowired
    private OpenChargeMapService ocmService;
    @Autowired
    private EstacaoRepository estacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @GetMapping("/proximas")
    public ResponseEntity<List<StationDTO>> buscarProximas(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "50") Double raio) {
        // Esta rota é pública ou não precisa de usuário, mantemos como está
        return ResponseEntity.ok(ocmService.buscarEstacoesProximas(lat, lon, raio));
    }

    @PostMapping("/{stationId}/favorito")
    public ResponseEntity<?> favoritar(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long stationId) {

        String token = extractToken(authHeader);
        if (token == null) return ResponseEntity.status(401).body("Token não fornecido ou inválido.");

        try {
            Long userId = tokenService.getUserIdFromToken(token);
            if (estacaoRepository.findByUsuario_IdUsuarioAndStationId(userId, stationId).isPresent()) {
                return ResponseEntity.badRequest().body("Estação já favoritada.");
            }

            Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            estacaoRepository.save(new Estacoes(stationId, usuario));

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro de autenticação.");
        }
    }

    @DeleteMapping("/{stationId}/favorito")
    public ResponseEntity<?> desfavoritar(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long stationId) {

        String token = extractToken(authHeader);
        if (token == null) return ResponseEntity.status(401).body("Token não fornecido ou inválido.");

        try {
            Long userId = tokenService.getUserIdFromToken(token);
            estacaoRepository.findByUsuario_IdUsuarioAndStationId(userId, stationId)
                    .ifPresent(estacaoRepository::delete);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro de autenticação.");
        }
    }

    @GetMapping("/favoritas")
    public ResponseEntity<?> listarFavoritas(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        String token = extractToken(authHeader);
        if (token == null) return ResponseEntity.status(401).body("Token não fornecido ou inválido.");

        try {
            Long userId = tokenService.getUserIdFromToken(token);
            List<Long> stationIds = estacaoRepository.findByUsuario_IdUsuario(userId)
                    .stream().map(Estacoes::getStationId).collect(Collectors.toList());

            List<StationDTO> detalhes = ocmService.buscarEstacoesPorIds(stationIds);
            return ResponseEntity.ok(detalhes);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro de autenticação.");
        }
    }
}