package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.dto.SalvarViagemDTO;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.entity.Viagens;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.repository.VeiculoRepository;
import com.fegcocco.emovebackend.repository.ViagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ViagemService {

    @Autowired
    private ViagemRepository viagemRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Viagens salvarViagem(Long usuarioId, SalvarViagemDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Veiculos veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        Viagens novaViagem = new Viagens();
        novaViagem.setUsuario(usuario);
        novaViagem.setVeiculo(veiculo);
        novaViagem.setKmTotal(dto.getKmTotal());
        novaViagem.setCo2Preservado(dto.getCo2Preservado());
        novaViagem.setDtViagem(LocalDate.now());
        novaViagem.setLatOrigem(dto.getLatOrigem());
        novaViagem.setLongiOrigem(dto.getLongiOrigem());
        novaViagem.setLatDestino(dto.getLatDestino());
        novaViagem.setLongiDestino(dto.getLongiDestino());
        novaViagem.setFavorita(dto.isFavorita());
        novaViagem.setApelido(dto.getApelido());

        return viagemRepository.save(novaViagem);
    }

    public List<Viagens> listarViagensPorUsuario(Long usuarioId, LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null) {
            return viagemRepository.findByUsuario_IdUsuarioAndDtViagemBetweenOrderByDtViagemDesc(usuarioId, inicio, fim);
        }
        // Se não passar data, retorna tudo
        return viagemRepository.findByUsuario_IdUsuarioOrderByDtViagemDesc(usuarioId);
    }

    public Viagens atualizarFavorito(Long usuarioId, Long viagemId, com.fegcocco.emovebackend.dto.AtualizarViagemDTO dto) {
        Viagens viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada"));

        //verifica se a viagem pertence ao usuário logado
        if (!viagem.getUsuario().getIdUsuario().equals(usuarioId)) {
            throw new RuntimeException("Acesso negado a esta viagem.");
        }

        viagem.setFavorita(dto.isFavorita());
        viagem.setApelido(dto.getApelido());

        return viagemRepository.save(viagem);
    }
}