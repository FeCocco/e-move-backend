package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.ViagemDTO;
import com.fegcocco.emovebackend.entity.Rotas;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.entity.Viagens;
import com.fegcocco.emovebackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ViagemService {

    @Autowired
    private ViagensRepository viagensRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private RotasRepository rotasRepository;

    public ViagemDTO registrarViagem(Long usuarioId, ViagemDTO viagemDTO) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Veiculos veiculo = veiculoRepository.findById(viagemDTO.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado."));
        Rotas rota = rotasRepository.findById(viagemDTO.getRotaId())
                .orElseThrow(() -> new RuntimeException("Rota não encontrada."));

        Viagens novaViagem = new Viagens();
        novaViagem.setUsuario(usuario);
        novaViagem.setVeiculo(veiculo);
        novaViagem.setRota(rota);
        novaViagem.setKmTotal(viagemDTO.getKmTotal());
        novaViagem.setCo2Preservado(viagemDTO.getCo2Preservado());
        novaViagem.setDtViagem(LocalDate.now());

        Viagens viagemSalva = viagensRepository.save(novaViagem);

        return new ViagemDTO(viagemSalva);
    }

    public Set<ViagemDTO> listarViagensDoUsuario(Long usuarioId) {

        Set<Viagens> viagens = viagensRepository.findByUsuarioId(usuarioId);

        return viagens.stream()
                .map(ViagemDTO::new)
                .collect(Collectors.toSet());
    }
}