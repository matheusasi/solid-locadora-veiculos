package br.com.locadora.contratos;

import br.com.locadora.dominio.Pagamento;

import java.util.List;

public interface PagamentoRepository {

    void salvar(Pagamento pagamento);

    List<Pagamento> buscarPorReserva(String reservaId);
}
