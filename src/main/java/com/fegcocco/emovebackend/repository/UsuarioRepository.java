package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Transactional
    @Modifying
    void deleteByAtivoFalseAndDataDesativacaoBefore(LocalDateTime dataLimite);

}
