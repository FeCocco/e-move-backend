package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Veiculos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculos, Long> {
    // A interface deve ficar vazia se você só precisa das operações CRUD padrão.
    // Métodos para queries customizadas podem ser adicionados aqui no futuro.
}