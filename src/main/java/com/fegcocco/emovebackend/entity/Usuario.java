package com.fegcocco.emovebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public enum Sexo {
        MASCULINO,
        FEMININO
    }

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Date dataNascimento;

    @Column(nullable = false)
    private String telefone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date dataCadastro;


    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<UsuarioVeiculo> veiculos = new HashSet<>();
}