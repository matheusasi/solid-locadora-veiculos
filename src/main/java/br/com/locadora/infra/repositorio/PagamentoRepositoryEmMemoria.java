package br.com.locadora.infra.repositorio;

import br.com.locadora.contratos.PagamentoRepository;
import br.com.locadora.dominio.Pagamento;

import java.util.ArrayList;
import java.util.List;

public class PagamentoRepositoryEmMemoria implements PagamentoRepository {

    private final List<Pagamento> pagamentos = new ArrayList<>();

    @Override
    public void salvar(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }
}
