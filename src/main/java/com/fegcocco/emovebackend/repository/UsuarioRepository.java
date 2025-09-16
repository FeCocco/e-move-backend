package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByAtivoFalseAndDataExclusaoBefore(Date data);

}
