package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.dto.CadastroDTO;
import com.fegcocco.emovebackend.dto.LoginDTO;
import com.fegcocco.emovebackend.dto.RespostaLoginDTO;
import com.fegcocco.emovebackend.dto.UsuarioDTO;
import com.fegcocco.emovebackend.entity.Usuario;
import com.fegcocco.emovebackend.repository.UsuarioRepository;
import com.fegcocco.emovebackend.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody CadastroDTO cadastroDTO) {

        if (UsuarioRepository.findByEmail(cadastroDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Este e-mail já está em uso.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(cadastroDTO.getNome());
        novoUsuario.setEmail(cadastroDTO.getEmail());
        novoUsuario.setCpf(cadastroDTO.getCpf());
        novoUsuario.setTelefone(cadastroDTO.getTelefone());
        novoUsuario.setDataNascimento(cadastroDTO.getDataNascimento());
        novoUsuario.setSenha(cadastroDTO.getSenha()); //adicionar um PasswordEncoder no futuro!

        novoUsuario.setDataCadastro(new Date());

        Usuario usuarioSalvo = UsuarioRepository.save(novoUsuario);

        return ResponseEntity.status(201).body(new UsuarioDTO(usuarioSalvo));
    }

    @GetMapping("/usuario/me")
    public ResponseEntity<?> getUsuarioLogado(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Cabeçalho de autorização ausente ou inválido.");
        }

        String token = authorizationHeader.substring(7);

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

}