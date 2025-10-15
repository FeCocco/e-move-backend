package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Rotas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RotasRepository extends JpaRepository<Rotas, Long> {

    @Query("SELECT r FROM Rotas r WHERE r.usuario.id_usuario = :usuarioId")
    Set<Rotas> findByUsuarioId(Long usuarioId);

}