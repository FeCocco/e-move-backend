package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecretString;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private Key jwtSecretKey;

    @PostConstruct
    public void init() {
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
    }

    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("nome", usuario.getNome());
        claims.put("email", usuario.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();


    }
}