package br.com.locadora.contratos;

import br.com.locadora.dominio.Pagamento;

public interface PagamentoRepository {

    void salvar(Pagamento pagamento);
}
