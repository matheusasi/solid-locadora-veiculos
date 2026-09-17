package br.com.locadora.contratos;

import java.math.BigDecimal;

public interface MetodoPagamento {

    boolean processar(BigDecimal valor);

    String getNome();
}
