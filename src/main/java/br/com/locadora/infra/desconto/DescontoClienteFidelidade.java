package br.com.locadora.infra.desconto;

import br.com.locadora.contratos.RegraDesconto;
import br.com.locadora.dominio.Reserva;

import java.math.BigDecimal;

public class DescontoClienteFidelidade implements RegraDesconto {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.05");

    @Override
    public BigDecimal calcularDesconto(Reserva reserva) {
        if (!reserva.getCliente().isFidelidade()) {
            return BigDecimal.ZERO;
        }
        return reserva.getValorBruto().multiply(PERCENTUAL);
    }
}
