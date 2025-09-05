package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioDTO {
    //private Long id;
    private String nome;
    private String email;
    private String telefone;
    private Date dataCadastro;
    private Usuario.Sexo sexo;
    private String cpf;
    private Date dataNascimento;

    public UsuarioDTO(Usuario usuario) {
        //this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.dataCadastro = usuario.getDataCadastro();
        this.sexo = usuario.getSexo();
        this.cpf = usuario.getCpf();
        this.dataNascimento = usuario.getDataNascimento();
    }
}