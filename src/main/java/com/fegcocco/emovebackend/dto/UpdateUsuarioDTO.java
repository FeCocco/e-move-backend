package com.fegcocco.emovebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsuarioDTO {
    private String nome;
    private String email;
    private String telefone;
    private String senha;
}
