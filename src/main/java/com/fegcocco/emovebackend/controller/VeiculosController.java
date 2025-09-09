package com.fegcocco.emovebackend.controller;

import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") //MUDAR EM PRODUCAO
public class VeiculosController {

    @Autowired
    VeiculoRepository veiculoRepository;

    @GetMapping("/veiculos")
    public List<Veiculos> findAll() {
        return veiculoRepository.findAll();
    }
}
