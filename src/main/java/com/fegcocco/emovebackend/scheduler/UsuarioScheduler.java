package com.fegcocco.emovebackend.scheduler;

import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class UsuarioScheduler {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void excluirUsuariosInativos() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        Date dataLimite = cal.getTime();

        List<Usuario> usuariosParaExcluir = usuarioRepository.findByAtivoFalseAndDataExclusaoBefore(dataLimite);

        if (!usuariosParaExcluir.isEmpty()) {
            usuarioRepository.deleteAll(usuariosParaExcluir);
            System.out.println(usuariosParaExcluir.size() + " usuários inativos foram excluídos permanentemente.");
        }
    }
}