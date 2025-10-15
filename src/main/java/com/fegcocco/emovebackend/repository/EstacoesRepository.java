package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Estacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EstacoesRepository extends JpaRepository<Estacoes, Long> {

    @Query("SELECT e FROM Estacoes e WHERE e.usuario.id_usuario = :usuarioId")
    Set<Estacoes> findByUsuarioId(Long usuarioId);

}