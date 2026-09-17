package br.com.locadora.contratos;

import br.com.locadora.dominio.Reserva;

import java.math.BigDecimal;

public interface CalculadoraPreco {

    BigDecimal calcular(Reserva reserva);
}
