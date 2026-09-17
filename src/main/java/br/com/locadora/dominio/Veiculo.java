package br.com.locadora.dominio;

import java.math.BigDecimal;

public class Veiculo {

    private final String placa;
    private final String modelo;
    private final BigDecimal valorDiaria;
    private boolean disponivel;

    public Veiculo(String placa, String modelo, BigDecimal valorDiaria) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("Placa é obrigatória");
        }
        if (valorDiaria == null || valorDiaria.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da diária deve ser maior que zero");
        }
        this.placa = placa;
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.disponivel = true;
    }

    public void reservar() {
        if (!disponivel) {
            throw new IllegalStateException("Veículo " + placa + " já está reservado");
        }
        this.disponivel = false;
    }

    public void liberar() {
        this.disponivel = true;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
