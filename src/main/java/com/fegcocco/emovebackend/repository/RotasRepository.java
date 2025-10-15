package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Rotas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RotasRepository extends JpaRepository<Rotas, Long> {

    Set<Rotas> findByUsuario_Id_usuario(Long usuarioId);

}