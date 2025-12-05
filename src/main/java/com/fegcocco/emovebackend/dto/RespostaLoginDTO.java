package com.fegcocco.emovebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespostaLoginDTO {
    private String nome;
    private String email;
}