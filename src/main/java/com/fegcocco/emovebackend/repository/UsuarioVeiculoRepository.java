package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.UsuarioVeiculo;
import com.fegcocco.emovebackend.entity.UsuarioVeiculoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioVeiculoRepository extends JpaRepository<UsuarioVeiculo, UsuarioVeiculoId> {
}