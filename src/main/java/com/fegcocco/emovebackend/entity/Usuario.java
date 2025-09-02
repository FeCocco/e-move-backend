package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public enum Sexo {
        MASCULINO,
        FEMININO
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cpf;

    private Date dataNascimento;

    private String telefone;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Date dataCadastro;

}
