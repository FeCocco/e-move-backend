package com.fegcocco.emovebackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
public class Veiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
