package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.VeiculoDTO; // Importar o DTO
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors; // Importar Collectors

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Este método pode retornar a entidade pois não tem lazy loading complexo
    @Transactional(readOnly = true)
    public List<Veiculos> listarTodosVeiculos() {
        return veiculoRepository.findAll();
    }

    // Método principal da mudança: retorna um Set de DTOs
    @Transactional(readOnly = true)
    public Set<VeiculoDTO> listarVeiculosDoUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Acessamos a lista e convertemos para DTO DENTRO da transação
        return usuario.getVeiculos().stream()
                .map(VeiculoDTO::new) // Converte cada Veiculo em VeiculoDTO
                .collect(Collectors.toSet());
    }

    // Este método também deve retornar o DTO
    @Transactional
    public Set<VeiculoDTO> adicionarVeiculoParaUsuario(Long usuarioId, Long veiculoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Veiculos veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        usuario.getVeiculos().add(veiculo);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioSalvo.getVeiculos().stream()
                .map(VeiculoDTO::new)
                .collect(Collectors.toSet());
    }

    // E este também
    @Transactional
    public Set<VeiculoDTO> removerVeiculoDoUsuario(Long usuarioId, Long veiculoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Veiculos veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        usuario.getVeiculos().remove(veiculo);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioSalvo.getVeiculos().stream()
                .map(VeiculoDTO::new)
                .collect(Collectors.toSet());
    }
}