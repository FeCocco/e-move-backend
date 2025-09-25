package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.VeiculoDTO;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.entity.UsuarioVeiculo;
import com.fegcocco.emovebackend.entity.UsuarioVeiculoId;
import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.repository.UsuarioVeiculoRepository;
import com.fegcocco.emovebackend.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioVeiculoRepository usuarioVeiculoRepository;

    public List<Veiculos> getAllVeiculos() {
        return veiculoRepository.findAll();
    }

    public Set<VeiculoDTO> listarVeiculosDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));

        return usuario.getVeiculos().stream()
                .map(associacao -> {
                    Veiculos veiculo = associacao.getVeiculo();
                    Integer nivelBateria = associacao.getNivelBateria() != null ? associacao.getNivelBateria() : 0;

                    // calcula para autonomia estimada
                    double autonomiaEstimada = veiculo.getAutonomia() * (nivelBateria / 100.0);

                    return new VeiculoDTO(
                            veiculo.getId(),
                            veiculo.getMarca(),
                            veiculo.getModelo(),
                            nivelBateria,
                            veiculo.getAutonomia(),
                            autonomiaEstimada
                    );
                })
                .collect(Collectors.toSet());
    }

    public Set<VeiculoDTO> adicionarVeiculoParaUsuario(Long usuarioId, Long veiculoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Veiculos veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        UsuarioVeiculoId id = new UsuarioVeiculoId(usuarioId, veiculoId);
        if (usuarioVeiculoRepository.existsById(id)) {
            throw new IllegalStateException("Veículo já adicionado.");
        }
        // Adiciona o veículo com 100% de bateria por padrão
        usuarioVeiculoRepository.save(new UsuarioVeiculo(id, usuario, veiculo, 100));
        return listarVeiculosDoUsuario(usuarioId);
    }

    public Set<VeiculoDTO> removerVeiculoDoUsuario(Long usuarioId, Long veiculoId) {
        UsuarioVeiculoId id = new UsuarioVeiculoId(usuarioId, veiculoId);
        if (!usuarioVeiculoRepository.existsById(id)) {
            throw new IllegalStateException("Associação entre usuário e veículo não encontrada.");
        }
        usuarioVeiculoRepository.deleteById(id);
        return listarVeiculosDoUsuario(usuarioId);
    }

    public UsuarioVeiculo atualizarNivelBateria(Long usuarioId, Long veiculoId, Integer nivelBateria) {
        if (nivelBateria < 0 || nivelBateria > 100) {
            throw new IllegalArgumentException("O nível da bateria deve ser entre 0 e 100.");
        }
        UsuarioVeiculoId id = new UsuarioVeiculoId(usuarioId, veiculoId);
        UsuarioVeiculo associacao = usuarioVeiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Associação não encontrada para este usuário e veículo."));
        associacao.setNivelBateria(nivelBateria);
        return usuarioVeiculoRepository.save(associacao);
    }
}