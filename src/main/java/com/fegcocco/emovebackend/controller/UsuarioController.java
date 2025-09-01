package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.LoginDTO;
import com.fegcocco.emovebackend.dto.RespostaLoginDTO;
import com.fegcocco.emovebackend.dto.UsuarioDTO;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.service.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Optional<Usuario> userOptional = UsuarioRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }

        Usuario user = userOptional.get();

        if (loginDTO.getSenha().equals(user.getSenha())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new RespostaLoginDTO(token));
        } else {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }
    }
}