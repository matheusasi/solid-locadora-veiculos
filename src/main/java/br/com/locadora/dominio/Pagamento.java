package br.com.locadora.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pagamento {

    private final String reservaId;
    private final BigDecimal valor;
    private final String metodo;
    private final boolean aprovado;
    private final LocalDateTime dataHora;

    public Pagamento(String reservaId, BigDecimal valor, String metodo, boolean aprovado) {
        if (reservaId == null || reservaId.isBlank()) {
            throw new IllegalArgumentException("Pagamento precisa estar vinculado a uma reserva");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor do pagamento não pode ser negativo");
        }
        this.reservaId = reservaId;
        this.valor = valor;
        this.metodo = metodo;
        this.aprovado = aprovado;
        this.dataHora = LocalDateTime.now();
    }

    public String getReservaId() {
        return reservaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getMetodo() {
        return metodo;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
