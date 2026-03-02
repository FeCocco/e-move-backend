package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //roda as 3 da manha
    @Scheduled(cron = "0 0 3 * * ?")
    public void limparUsuariosDesativados() {
        // Calcula qual era a data exata 30 dias atrás
        LocalDateTime limite = LocalDateTime.now().minusDays(30);

        System.out.println("Iniciando rotina de exclusão de usuários desativados antes de: " + limite);

        usuarioRepository.deleteByAtivoFalseAndDataDesativacaoBefore(limite);
    }
}
