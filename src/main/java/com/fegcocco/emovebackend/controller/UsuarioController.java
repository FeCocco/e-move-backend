package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.*;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class UsuarioController {

    @Autowired
    UsuarioRepository UsuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* Metodo auxiliar para pegar o token do Header
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    } */

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO data) {
        Optional<Usuario> userOptional = UsuarioRepository.findByEmail(data.getEmail());

        if (userOptional.isEmpty() || !passwordEncoder.matches(data.getSenha(), userOptional.get().getSenha())) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }

        Usuario user = userOptional.get();
        String token = tokenService.generateToken(user);

        if (user.getAtivo() == false) {
            Usuario usuario = userOptional.get();
            usuario.setAtivo(true);
            UsuarioRepository.save(usuario);
        }

        return ResponseEntity.ok(new RespostaLoginDTO(user.getNome(), user.getEmail(), token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().body("Logout realizado com sucesso.");
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody CadastroDTO data) {
        if (UsuarioRepository.findByEmail(data.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Este e-mail já está em uso.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(data.getNome());
        novoUsuario.setEmail(data.getEmail());
        novoUsuario.setTelefone(data.getTelefone());
        novoUsuario.setSexo(data.getSexo());
        novoUsuario.setDataNascimento(data.getDataNascimento());
        novoUsuario.setSenha(passwordEncoder.encode(data.getSenha()));

        Usuario usuarioSalvo = UsuarioRepository.save(novoUsuario);
        return ResponseEntity.status(201).body(new UsuarioDTO(usuarioSalvo));
    }

    @GetMapping("/usuario/me")
    public ResponseEntity<?> getUsuarioLogado(HttpServletRequest request) {
        String token = tokenService.resolveToken(request);

        if (token == null) return ResponseEntity.status(401).body("Token não encontrado.");
        if (!tokenService.isTokenValid(token)) return ResponseEntity.status(401).body("Token inválido ou expirado.");

        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Optional<Usuario> usuarioOptional = UsuarioRepository.findById(usuarioId);

            if (usuarioOptional.isPresent()) {
                return ResponseEntity.ok(new UsuarioDTO(usuarioOptional.get()));
            } else {
                return ResponseEntity.status(404).body("Usuário não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro ao processar token.");
        }
    }

    @PutMapping("/usuario/me")
    public ResponseEntity<?> updateUsuario(HttpServletRequest request, @Valid @RequestBody UpdateUsuarioDTO data) {
        String token = tokenService.resolveToken(request);

        if (token == null || !tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Token inválido.");
        }

        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Optional<Usuario> usuarioOptional = UsuarioRepository.findById(usuarioId);

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Usuário não encontrado.");
            }

            Usuario usuario = usuarioOptional.get();

            if (!usuario.getEmail().equals(data.getEmail()) && UsuarioRepository.findByEmail(data.getEmail()).isPresent()) {
                return ResponseEntity.status(409).body("Este e-mail já está em uso.");
            }

            usuario.setNome(data.getNome());
            usuario.setEmail(data.getEmail());
            usuario.setTelefone(data.getTelefone());
            usuario.setSexo(data.getSexo());

            if (data.getSenha() != null && !data.getSenha().isBlank()) {
                if (data.getSenha().length() < 8) return ResponseEntity.status(400).body("Senha muito curta.");
                usuario.setSenha(passwordEncoder.encode(data.getSenha()));
            }

            return ResponseEntity.ok(new UsuarioDTO(UsuarioRepository.save(usuario)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar usuário.");
        }
    }

    @DeleteMapping("/usuario/me")
    public ResponseEntity<?> deleteUsuario(HttpServletRequest request) {
        String token = tokenService.resolveToken(request);

        if (token == null || !tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Token inválido.");
        }

        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Optional<Usuario> usuarioOptional = UsuarioRepository.findById(usuarioId);

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Usuário não encontrado.");
            }

            Usuario usuario = usuarioOptional.get();
            usuario.setAtivo(false);
            UsuarioRepository.save(usuario);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno: " + e.getMessage());
        }
    }
}