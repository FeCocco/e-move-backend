package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String email;
    private String telefone;
    private Date dataCadastro;
    private Usuario.Sexo sexo;
    private Date dataNascimento;

    public UsuarioDTO(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.dataCadastro = usuario.getDataCadastro();
        this.sexo = usuario.getSexo();
        this.dataNascimento = usuario.getDataNascimento();
    }
}