package com.fegcocco.emovebackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fegcocco.emovebackend.dto.GeocodingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GeocodingServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private GeocodingService geocodingService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(geocodingService, "apiKey", "chave-falsa");
        ReflectionTestUtils.setField(geocodingService, "apiUrl", "http://fake-api.com/search");
        ReflectionTestUtils.setField(geocodingService, "directionsApiUrl", "http://fake-api.com/directions");
    }

    @Test
    void deveRetornarListaDeGeocodingComSucesso() throws Exception {
        String enderecoBuscado = "Avenida Paulista";
        String urlEsperada = "http://fake-api.com/search?key=chave-falsa&q=Avenida Paulista&format=json&limit=5";

        // simula o formato de JSON que o LocationIQ devolve
        String jsonRespostaDaApi = "[{\"lat\":\"-23.561\",\"lon\":\"-46.656\",\"display_name\":\"Avenida Paulista, SP\"}]";

        // quando o código tentar fazer um GET nessa URL, devolve meu JSON falso
        Mockito.when(restTemplate.getForObject(urlEsperada, String.class))
                .thenReturn(jsonRespostaDaApi);

        List<GeocodingDTO> resultado = geocodingService.forwardGeocode(enderecoBuscado);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Avenida Paulista", resultado.get(0).getAddress());
        assertEquals(-23.561, resultado.get(0).getLatitude());
        assertEquals("Avenida Paulista, SP", resultado.get(0).getDisplayName());
    }
}