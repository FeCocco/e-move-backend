package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Estacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EstacoesRepository extends JpaRepository<Estacoes, Long> {

    Set<Estacoes> findByUsuario_Id_usuario(Long usuarioId);

}