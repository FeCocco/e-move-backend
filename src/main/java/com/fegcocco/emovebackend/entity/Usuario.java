package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "USUARIOS")
@Getter
@Setter
public class Usuario {

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cpf;

    private Date dataNascimento;

    public enum Sexo {
        MASCULINO,
        FEMININO
    }

    private int Telefone;

    private String email;

    private String senha;

    private Date dataCadastro;

}
