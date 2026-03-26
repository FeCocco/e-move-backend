package com.fegcocco.emovebackend.service;

import com.fegcocco.emovebackend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Clock clock; // Mockamos o relógio

    @InjectMocks
    private UsuariosService usuariosService;

    @Test
    void deveLimparUsuariosDesativadosExatamenteHa30Dias() {
        String dataFixa = "2026-03-26T12:00:00Z";
        Clock relogioFixo = Clock.fixed(Instant.parse(dataFixa), ZoneId.of("UTC"));

        Mockito.when(clock.instant()).thenReturn(relogioFixo.instant());
        Mockito.when(clock.getZone()).thenReturn(relogioFixo.getZone());

        LocalDateTime dataEsperada = LocalDateTime.now(relogioFixo).minusDays(30);

        usuariosService.limparUsuariosDesativados();

        Mockito.verify(usuarioRepository, Mockito.times(1))
                .deleteByAtivoFalseAndDataDesativacaoBefore(dataEsperada);
    }
}