package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.RotaDTO;
import com.fegcocco.emovebackend.entity.Rotas;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.RotasRepository;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class RotaService {

    @Autowired
    private RotasRepository rotasRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RotaDTO salvarRotaFavorita(Long usuarioId, RotaDTO rotaDTO) {
        // busca o usuario no banco de dados, se não encontrar, lança uma exceção.
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));

        // cria uma nova entidade Rotas e preenche com os dados do DTO.
        Rotas novaRota = new Rotas();
        novaRota.setUsuario(usuario); // Associa a rota ao usuário encontrado.
        novaRota.setNome_rota(rotaDTO.getNomeRota());
        novaRota.setLat(rotaDTO.getLat());
        novaRota.setLongi(rotaDTO.getLongi());
        novaRota.setKmTotal(rotaDTO.getKmTotal());

        // salva a nova rota no banco de dados.
        Rotas rotaSalva = rotasRepository.save(novaRota);

        return new RotaDTO(rotaSalva);
    }

    public Set<RotaDTO> listarRotasDoUsuario(Long usuarioId) {

        Set<Rotas> rotas = rotasRepository.findByUsuario_Id_usuario(usuarioId);

        return rotas.stream()
                .map(RotaDTO::new)
                .collect(Collectors.toSet());
    }
}