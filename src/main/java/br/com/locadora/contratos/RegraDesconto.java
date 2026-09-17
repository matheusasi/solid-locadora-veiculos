package br.com.locadora.contratos;

import br.com.locadora.dominio.Reserva;

import java.math.BigDecimal;

public interface RegraDesconto {

    BigDecimal calcularDesconto(Reserva reserva);
}
