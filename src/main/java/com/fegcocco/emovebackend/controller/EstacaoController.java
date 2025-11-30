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

    @GetMapping("/proximas")
    public ResponseEntity<List<StationDTO>> buscarProximas(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "50") Double raio) {
        return ResponseEntity.ok(ocmService.buscarEstacoesProximas(lat, lon, raio));
    }

    // Favoritar
    @PostMapping("/{stationId}/favorito")
    public ResponseEntity<?> favoritar(
            @CookieValue(name = "e-move-token") String token,
            @PathVariable Long stationId) {

        System.out.println(">>> Requisição chegou no controller. StationID: " + stationId); // LOG DE DEBUG

            Long userId = tokenService.getUserIdFromToken(token);
            System.out.println(">>> User ID extraído: " + userId); // LOG DE DEBUG

            if (estacaoRepository.findByUsuario_IdUsuarioAndStationId(userId, stationId).isPresent()) {
                return ResponseEntity.badRequest().body("Estação já favoritada.");
            }

            Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            estacaoRepository.save(new Estacoes(stationId, usuario));

            return ResponseEntity.ok().build();

    }

    // Desfavoritar
    @DeleteMapping("/{stationId}/favorito")
    public ResponseEntity<?> desfavoritar(
            @CookieValue(name = "e-move-token") String token,
            @PathVariable Long stationId) {
        Long userId = tokenService.getUserIdFromToken(token);

        estacaoRepository.findByUsuario_IdUsuarioAndStationId(userId, stationId)
                .ifPresent(estacaoRepository::delete);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/favoritas")
    public ResponseEntity<List<StationDTO>> listarFavoritas(@CookieValue(name = "e-move-token") String token) {
        Long userId = tokenService.getUserIdFromToken(token);

        List<Long> stationIds = estacaoRepository.findByUsuario_IdUsuario(userId)
                .stream().map(Estacoes::getStationId).collect(Collectors.toList());

        List<StationDTO> detalhes = ocmService.buscarEstacoesPorIds(stationIds);

        return ResponseEntity.ok(detalhes);
    }
}