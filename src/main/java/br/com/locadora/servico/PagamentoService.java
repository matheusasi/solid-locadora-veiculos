package br.com.locadora.servico;

import br.com.locadora.contratos.CanalNotificacao;
import br.com.locadora.contratos.MetodoPagamento;
import br.com.locadora.contratos.PagamentoRepository;
import br.com.locadora.contratos.ReservaRepository;
import br.com.locadora.dominio.Pagamento;
import br.com.locadora.dominio.Reserva;

public class PagamentoService {

    private final ReservaRepository reservaRepository;
    private final PagamentoRepository pagamentoRepository;
    private final MetodoPagamento metodoPagamento;
    private final CanalNotificacao canalNotificacao;

    public PagamentoService(ReservaRepository reservaRepository,
                            PagamentoRepository pagamentoRepository,
                            MetodoPagamento metodoPagamento,
                            CanalNotificacao canalNotificacao) {
        this.reservaRepository = reservaRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.metodoPagamento = metodoPagamento;
        this.canalNotificacao = canalNotificacao;
    }

    public Pagamento pagar(String reservaId) {
        Reserva reserva = reservaRepository.buscarPorId(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada: " + reservaId));

        boolean aprovado = metodoPagamento.processar(reserva.getValorTotal());
        Pagamento pagamento = new Pagamento(reserva.getId(), reserva.getValorTotal(), metodoPagamento.getNome(), aprovado);
        pagamentoRepository.salvar(pagamento);

        if (aprovado) {
            reserva.confirmar();
            canalNotificacao.enviar(reserva.getCliente(), montarMensagemConfirmacao(reserva));
        } else {
            reserva.cancelar();
            canalNotificacao.enviar(reserva.getCliente(),
                    "Pagamento da reserva " + reserva.getId() + " não foi aprovado. A reserva foi cancelada.");
        }

        reservaRepository.salvar(reserva);
        return pagamento;
    }

    private String montarMensagemConfirmacao(Reserva reserva) {
        return "Reserva " + reserva.getId() + " confirmada. Veículo " + reserva.getVeiculo().getModelo()
                + " de " + reserva.getDataInicio() + " até " + reserva.getDataFim()
                + ". Total pago via " + metodoPagamento.getNome() + ": R$ " + reserva.getValorTotal();
    }
}
