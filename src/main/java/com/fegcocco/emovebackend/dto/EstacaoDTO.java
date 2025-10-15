package com.fegcocco.emovebackend.dto;

import com.fegcocco.emovebackend.entity.Estacoes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EstacaoDTO {

    private Long id;
    //********** Implementar mais dados após API **********

    public EstacaoDTO(Estacoes estacao) {
        this.id = estacao.getId();
    }
}