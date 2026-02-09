package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.*;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginDTO data,
            HttpServletResponse response) {

        Optional<Usuario> userOptional = UsuarioRepository.findByEmail(data.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }

        Usuario user = userOptional.get();

        if (passwordEncoder.matches(data.getSenha(), user.getSenha())) {

            String token = tokenService.generateToken(user);

            // Configuração do Cookie
            ResponseCookie cookie = ResponseCookie.from("e-move-token", token)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .sameSite("None") //para Cross-Site (Vercel -> Render)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok(new RespostaLoginDTO(user.getNome(), user.getEmail()));
        } else {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // novo cookie com o mesmo nome, valor nulo e tempo de vida 0.
        ResponseCookie cookie = ResponseCookie.from("e-move-token", "")
                .httpOnly(true)
                .secure(true) //
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

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
    public ResponseEntity<?> getUsuarioLogado(@CookieValue(name = "e-move-token", required = false) String token) {

        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(401).body("Token de autenticação não encontrado.");
        }

        if (!tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }

        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Optional<Usuario> usuarioOptional = UsuarioRepository.findById(usuarioId);

            if (usuarioOptional.isPresent()) {
                UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioOptional.get());
                return ResponseEntity.ok(usuarioDTO);
            } else {
                return ResponseEntity.status(404).body("Usuário não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro ao processar o token.");
        }
    }

    @PutMapping("/usuario/me")
    public ResponseEntity<?> updateUsuario(
            @CookieValue(name = "e-move-token") String token,
            @Valid @RequestBody UpdateUsuarioDTO data) {

        if (token == null || !tokenService.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Token inválido ou expirado.");
        }

        try {
            Long usuarioId = tokenService.getUserIdFromToken(token);
            Optional<Usuario> usuarioOptional = UsuarioRepository.findById(usuarioId);

            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Usuário não encontrado.");
            }

            Usuario usuario = usuarioOptional.get();

            if (!usuario.getEmail().equals(data.getEmail())) {

                if (UsuarioRepository.findByEmail(data.getEmail()).isPresent()) {
                    return ResponseEntity.status(409).body("Este e-mail já está em uso por outra conta.");
                }
            }

            usuario.setNome(data.getNome());
            usuario.setEmail(data.getEmail());
            usuario.setTelefone(data.getTelefone());
            usuario.setSexo(data.getSexo());

            if (data.getSenha() != null && !data.getSenha().isBlank()) {
                if (data.getSenha().length() < 8) {
                    return ResponseEntity.status(400).body("A nova senha deve ter no mínimo 8 caracteres.");
                }
                usuario.setSenha(passwordEncoder.encode(data.getSenha()));
            }

            Usuario usuarioAtualizado = UsuarioRepository.save(usuario);

            return ResponseEntity.ok(new UsuarioDTO(usuarioAtualizado));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocorreu um erro interno ao tentar atualizar o usuário.");
        }
    }

}