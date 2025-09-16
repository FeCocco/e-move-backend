package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.BooleanFlag;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public enum Sexo {
        MASCULINO,
        FEMININO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String cpf;

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

    @NotNull
    @ColumnDefault("true")
    private boolean ativo = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExclusao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USUARIOS_VEICULOS",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_VEICULO")
    )

    private Set<Veiculos> veiculos = new HashSet<>();
}