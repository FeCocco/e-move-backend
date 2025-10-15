package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.EstacaoDTO;
import com.fegcocco.emovebackend.entity.Estacoes;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.EstacoesRepository;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstacaoService {

    @Autowired
    private EstacoesRepository estacoesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public EstacaoDTO salvarEstacaoFavorita(Long usuarioId) {
        //busca o usuário, se não existir, lança uma exceção.
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + usuarioId));

        // cria uma nova entidade Estacoes e associa ao usuário.
        // (no futuro, recebe o ID da estação externa aqui via API)
        Estacoes novaEstacao = new Estacoes();
        novaEstacao.setUsuario(usuario);

        // salva a nova estação favorita no banco.
        Estacoes estacaoSalva = estacoesRepository.save(novaEstacao);

        return new EstacaoDTO(estacaoSalva);
    }

    public Set<EstacaoDTO> listarEstacoesDoUsuario(Long usuarioId) {

        Set<Estacoes> estacoes = estacoesRepository.findByUsuarioId(usuarioId);

        return estacoes.stream()
                .map(EstacaoDTO::new)
                .collect(Collectors.toSet());
    }
}