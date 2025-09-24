package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Veiculos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculos, Long> {
}