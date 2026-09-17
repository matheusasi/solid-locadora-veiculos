package br.com.locadora.servico;

import br.com.locadora.contratos.CalculadoraPreco;
import br.com.locadora.contratos.RegraDesconto;
import br.com.locadora.dominio.Reserva;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculadoraPrecoComDescontos implements CalculadoraPreco {

    private final List<RegraDesconto> regrasDesconto;

    public CalculadoraPrecoComDescontos(List<RegraDesconto> regrasDesconto) {
        this.regrasDesconto = List.copyOf(regrasDesconto);
    }

    @Override
    public BigDecimal calcular(Reserva reserva) {
        BigDecimal descontoTotal = regrasDesconto.stream()
                .map(regra -> regra.calcularDesconto(reserva))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return reserva.getValorBruto()
                .subtract(descontoTotal)
                .max(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
