package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Viagens;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate; // <-- Importante: Importe o LocalDate
import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagens, Long> {

    List<Viagens> findByUsuario_IdUsuarioOrderByDtViagemDesc(Long usuarioId);

    List<Viagens> findByUsuario_IdUsuarioAndDtViagemBetweenOrderByDtViagemDesc(Long usuarioId, LocalDate inicio, LocalDate fim);
}