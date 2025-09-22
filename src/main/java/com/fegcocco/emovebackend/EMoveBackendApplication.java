package com.fegcocco.emovebackend;

import com.fegcocco.emovebackend.entity.Veiculos;
import com.fegcocco.emovebackend.repository.VeiculoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class EMoveBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMoveBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(VeiculoRepository veiculoRepository) {
        return args -> {
            if (veiculoRepository.count() > 0) {
                System.out.println(">>> Base de veículos já populada.");
                return;
            }

            System.out.println(">>> Populando base de dados de veículos...");

            List<Veiculos> veiculosIniciais = Arrays.asList(
                    new Veiculos("Tesla", "Model 3", 491, Veiculos.TipoPlugin.SAE_TIPO_1),
                    new Veiculos("Nissan", "Leaf", 270, Veiculos.TipoPlugin.CHADEMO),
                    new Veiculos("Chevrolet", "Bolt EV", 416, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Audi", "E-tron", 357, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Hyundai", "Kona Eletric", 415, Veiculos.TipoPlugin.SAE_TIPO_1),
                    new Veiculos("BMW", "i3", 246, Veiculos.TipoPlugin.SAE_TIPO_1),
                    new Veiculos("Ford", "Mustang Mach-E", 483, Veiculos.TipoPlugin.SAE_TIPO_1),
                    new Veiculos("Volkswagen", "ID.4", 402, Veiculos.TipoPlugin.SAE_TIPO_1),
                    new Veiculos("Kia", "Niro EV", 385, Veiculos.TipoPlugin.SAE_TIPO_1),
                    new Veiculos("Porsche", "Taycan", 323, Veiculos.TipoPlugin.TIPO_2_AC),
                    new Veiculos("BYD", "Dolphin", 291, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("BYD", "Dolphin Plus", 330, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("BYD", "Seal", 372, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("BYD", "Yuan Plus", 294, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("GWM", "Ora 03 Skin", 232, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("GWM", "Ora 03 GT", 232, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Renault", "Kwid E-Tech", 185, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Volvo", "EX30", 250, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Volvo", "XC40 Recharge Plus", 326, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Volvo", "C40 Recharge Plus", 332, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Peugeot", "e-2008", 234, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Peugeot", "e-208 GT", 220, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("JAC", "E-JS1", 161, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Caoa Chery", "iCar", 197, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Mini", "Cooper S E (Elétrico)", 161, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Nissan", "Leaf", 272, Veiculos.TipoPlugin.CHADEMO),
                    new Veiculos("Chevrolet", "Bolt EV", 251, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("BMW", "iX3", 305, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("BMW", "i4", 350, Veiculos.TipoPlugin.CCS_TIPO_2),
                    new Veiculos("Renault", "Megane E-Tech", 337, Veiculos.TipoPlugin.CCS_TIPO_2)
            );

            veiculoRepository.saveAll(veiculosIniciais);
            System.out.println(">>> " + veiculosIniciais.size() + " veículos inseridos com sucesso.");
        };
    }
}