package br.com.locadora.infra.desconto;

import br.com.locadora.contratos.RegraDesconto;
import br.com.locadora.dominio.Reserva;

import java.math.BigDecimal;

public class DescontoLongaDuracao implements RegraDesconto {

    private static final long DIAS_MINIMOS = 7;
    private static final BigDecimal PERCENTUAL = new BigDecimal("0.10");

    @Override
    public BigDecimal calcularDesconto(Reserva reserva) {
        if (reserva.getQuantidadeDias() < DIAS_MINIMOS) {
            return BigDecimal.ZERO;
        }
        return reserva.getValorBruto().multiply(PERCENTUAL);
    }
}
