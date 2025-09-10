package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Usuario;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class CadastroDTO {

    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "O formato do e-mail é inválido.")
    private String email;

    @NotBlank(message = "O CPF não pode estar em branco.")
    private String cpf;

    @NotBlank(message = "O telefone não pode estar em branco.")
    @Size(min = 10, max = 11, message = "O telefone deve ter até 11 dígitos")
    private String telefone;

    //*****Verificar @NotBlank*****
    private Usuario.Sexo sexo;

    //*****Verificar @NotBlank*****
    private Date dataNascimento;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String senha;
}
