package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Estacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EstacaoRepository extends JpaRepository<Estacoes, Long> {
    List<Estacoes> findByUsuario_IdUsuario(Long usuarioId);
    Optional<Estacoes> findByUsuario_IdUsuarioAndStationId(Long usuarioId, Long stationId);
}