package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.CadastroDTO;
import com.fegcocco.emovebackend.dto.LoginDTO;
import com.fegcocco.emovebackend.dto.UpdateUsuarioDTO;
import com.fegcocco.emovebackend.dto.UsuarioDTO;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") //MUDAR EM PRODUCAO
public class UsuarioController {

    @Autowired
    UsuarioRepository UsuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        Optional<Usuario> userOptional = UsuarioRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }

        Usuario user = userOptional.get();

        if (loginDTO.getSenha().equals(user.getSenha())) {
            String token = tokenService.generateToken(user);

            Cookie cookie = new Cookie("e-move-token", token);

            // propriedades de segurança
            cookie.setHttpOnly(true); // Impede acesso via JavaScript
            cookie.setSecure(false); // Enviar apenas sobre HTTPS (MUDAR EM PROODUCAO)
            cookie.setPath("/");
            cookie.setMaxAge(24 * 60 * 60); // Expira em 24 horas
            // cookie.setSameSite("Strict"); // Protecao extra contra CSRF (MUDAR EM PROODUCAO)

            response.addCookie(cookie);

            return ResponseEntity.ok(new UsuarioDTO(user));
        } else {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // novo cookie com o mesmo nome, valor nulo e tempo de vida 0.
        Cookie cookie = new Cookie("e-move-token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); //expirar o cookie imediatamente
        // Adiciona o cookie "expirado" à resposta.
        response.addCookie(cookie);

        return ResponseEntity.ok().body("Logout realizado com sucesso.");
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody CadastroDTO cadastroDTO) {

        if (UsuarioRepository.findByEmail(cadastroDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Este e-mail já está em uso.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(cadastroDTO.getNome());
        novoUsuario.setEmail(cadastroDTO.getEmail());
        novoUsuario.setTelefone(cadastroDTO.getTelefone());
        novoUsuario.setSexo(cadastroDTO.getSexo());
        novoUsuario.setDataNascimento(cadastroDTO.getDataNascimento());
        novoUsuario.setSenha(cadastroDTO.getSenha()); //*********ADICIONAR PASSWORD ENCODER*************


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
    public ResponseEntity<?> updateUsuario(@CookieValue(name = "e-move-token") String token, @Valid @RequestBody UpdateUsuarioDTO updateDTO) {

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

            if (!usuario.getEmail().equals(updateDTO.getEmail())) {

                if (UsuarioRepository.findByEmail(updateDTO.getEmail()).isPresent()) {
                    return ResponseEntity.status(409).body("Este e-mail já está em uso por outra conta.");
                }
            }

            usuario.setNome(updateDTO.getNome());
            usuario.setEmail(updateDTO.getEmail());
            usuario.setTelefone(updateDTO.getTelefone());
            usuario.setSexo(updateDTO.getSexo());

            if (updateDTO.getSenha() != null && !updateDTO.getSenha().isBlank()) {
                if (updateDTO.getSenha().length() < 8) {
                    return ResponseEntity.status(400).body("A nova senha deve ter no mínimo 8 caracteres.");
                }
                usuario.setSenha(updateDTO.getSenha());//*********ADICIONAR PASSWORD ENCODER*************
            }

            Usuario usuarioAtualizado = UsuarioRepository.save(usuario);

            return ResponseEntity.ok(new UsuarioDTO(usuarioAtualizado));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ocorreu um erro interno ao tentar atualizar o usuário.");
        }
    }

}