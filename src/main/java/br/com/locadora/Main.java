package br.com.locadora;

import br.com.locadora.contratos.CalculadoraPreco;
import br.com.locadora.contratos.PagamentoRepository;
import br.com.locadora.contratos.ReservaRepository;
import br.com.locadora.contratos.VeiculoRepository;
import br.com.locadora.dominio.Cliente;
import br.com.locadora.dominio.Pagamento;
import br.com.locadora.dominio.Reserva;
import br.com.locadora.dominio.Veiculo;
import br.com.locadora.infra.desconto.DescontoClienteFidelidade;
import br.com.locadora.infra.desconto.DescontoLongaDuracao;
import br.com.locadora.infra.notificacao.NotificacaoEmail;
import br.com.locadora.infra.notificacao.NotificacaoWhatsApp;
import br.com.locadora.infra.pagamento.PagamentoCartaoCredito;
import br.com.locadora.infra.pagamento.PagamentoPix;
import br.com.locadora.infra.repositorio.PagamentoRepositoryEmMemoria;
import br.com.locadora.infra.repositorio.ReservaRepositoryEmMemoria;
import br.com.locadora.infra.repositorio.VeiculoRepositoryEmMemoria;
import br.com.locadora.servico.CalculadoraPrecoComDescontos;
import br.com.locadora.servico.PagamentoService;
import br.com.locadora.servico.ReservaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        VeiculoRepository veiculoRepository = new VeiculoRepositoryEmMemoria();
        ReservaRepository reservaRepository = new ReservaRepositoryEmMemoria();
        PagamentoRepository pagamentoRepository = new PagamentoRepositoryEmMemoria();

        veiculoRepository.salvar(new Veiculo("ABC1D23", "Fiat Mobi", new BigDecimal("120.00")));
        veiculoRepository.salvar(new Veiculo("XYZ9K87", "Jeep Compass", new BigDecimal("350.00")));
        veiculoRepository.salvar(new Veiculo("QWE4R56", "Toyota Corolla", new BigDecimal("280.00")));

        CalculadoraPreco calculadora = new CalculadoraPrecoComDescontos(
                List.of(new DescontoLongaDuracao(), new DescontoClienteFidelidade()));

        ReservaService reservaService = new ReservaService(veiculoRepository, reservaRepository, calculadora);

        Cliente ana = new Cliente("Ana Souza", "12345678901", "ana@email.com", "(47) 99999-1111", true);
        Cliente bruno = new Cliente("Bruno Lima", "98765432100", "bruno@email.com", "(47) 98888-2222", false);

        System.out.println("=== Cenário 1: Pix + WhatsApp ===");
        PagamentoService pagamentoPixWhatsApp = new PagamentoService(
                reservaRepository, pagamentoRepository,
                new PagamentoPix("locadora@pix.com.br"), new NotificacaoWhatsApp());

        Reserva reserva1 = reservaService.criarReserva(ana, "XYZ9K87",
                LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 9));
        imprimirResumo(reserva1, pagamentoPixWhatsApp.pagar(reserva1.getId()));

        System.out.println();
        System.out.println("=== Cenário 2: Cartão de Crédito + E-mail ===");
        PagamentoService pagamentoCartaoEmail = new PagamentoService(
                reservaRepository, pagamentoRepository,
                new PagamentoCartaoCredito("4321", new BigDecimal("1000.00"), 3), new NotificacaoEmail());

        Reserva reserva2 = reservaService.criarReserva(bruno, "ABC1D23",
                LocalDate.of(2026, 10, 5), LocalDate.of(2026, 10, 8));
        imprimirResumo(reserva2, pagamentoCartaoEmail.pagar(reserva2.getId()));

        System.out.println();
        System.out.println("=== Cenário 3: Cartão recusado por limite + E-mail ===");
        Reserva reserva3 = reservaService.criarReserva(bruno, "QWE4R56",
                LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 6));
        imprimirResumo(reserva3, pagamentoCartaoEmail.pagar(reserva3.getId()));
        System.out.println("Veículo " + reserva3.getVeiculo().getPlaca() + " disponível novamente: "
                + reserva3.getVeiculo().isDisponivel());
    }

    private static void imprimirResumo(Reserva reserva, Pagamento pagamento) {
        String situacaoPagamento = pagamento.isAprovado() ? "aprovado" : "recusado";
        System.out.println("Reserva " + reserva.getId()
                + " | " + reserva.getCliente().getNome()
                + " | " + reserva.getVeiculo().getModelo()
                + " | " + reserva.getQuantidadeDias() + " diárias"
                + " | Bruto R$ " + reserva.getValorBruto()
                + " | Total R$ " + reserva.getValorTotal()
                + " | Pagamento " + pagamento.getMetodo() + " " + situacaoPagamento
                + " | Status " + reserva.getStatus());
    }
}
