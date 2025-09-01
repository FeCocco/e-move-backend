package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.LoginDTO;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    UsuarioRepository UsuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Usuario> userOptional = UsuarioRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }

        Usuario user = userOptional.get();

        if (loginDTO.getSenha().equals(user.getSenha())) {
            user.setSenha(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }
    }

}
