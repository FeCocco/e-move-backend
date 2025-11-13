package com.fegcocco.emovebackend.repository;

import com.fegcocco.emovebackend.entity.Viagens;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ViagemRepository extends JpaRepository<Viagens, Long> {
    //encontra todas as viagens de um usuário, ordenadas pela mais recente
    List<Viagens> findByUsuario_IdUsuarioOrderByDtViagemDesc(Long usuarioId);
}