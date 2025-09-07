package com.fegcocco.emovebackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsuarioDTO {
    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "O formato do e-mail é inválido.")
    private String email;

    @NotBlank(message = "O telefone não pode estar em branco.")
    @Size(min = 10, max = 11, message = "O telefone deve ter até 11 dígitos")
    private String telefone;

    private String senha;
}
