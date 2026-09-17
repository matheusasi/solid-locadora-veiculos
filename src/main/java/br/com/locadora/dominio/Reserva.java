package br.com.locadora.dominio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Reserva {

    private final String id;
    private final Cliente cliente;
    private final Veiculo veiculo;
    private final LocalDate dataInicio;
    private final LocalDate dataFim;
    private BigDecimal valorTotal;
    private StatusReserva status;

    public Reserva(Cliente cliente, Veiculo veiculo, LocalDate dataInicio, LocalDate dataFim) {
        if (cliente == null || veiculo == null) {
            throw new IllegalArgumentException("Cliente e veículo são obrigatórios");
        }
        if (dataInicio == null || dataFim == null || !dataFim.isAfter(dataInicio)) {
            throw new IllegalArgumentException("Data final deve ser posterior à data inicial");
        }
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = BigDecimal.ZERO;
        this.status = StatusReserva.PENDENTE;
    }

    public long getQuantidadeDias() {
        return ChronoUnit.DAYS.between(dataInicio, dataFim);
    }

    public BigDecimal getValorBruto() {
        return veiculo.getValorDiaria().multiply(BigDecimal.valueOf(getQuantidadeDias()));
    }

    public void definirValorTotal(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor total não pode ser negativo");
        }
        if (status != StatusReserva.PENDENTE) {
            throw new IllegalStateException("Valor só pode ser alterado em reservas pendentes");
        }
        this.valorTotal = valor;
    }

    public void confirmar() {
        if (status != StatusReserva.PENDENTE) {
            throw new IllegalStateException("Apenas reservas pendentes podem ser confirmadas");
        }
        this.status = StatusReserva.CONFIRMADA;
    }

    public void cancelar() {
        if (status == StatusReserva.CANCELADA) {
            throw new IllegalStateException("Reserva já está cancelada");
        }
        this.status = StatusReserva.CANCELADA;
        veiculo.liberar();
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusReserva getStatus() {
        return status;
    }
}
