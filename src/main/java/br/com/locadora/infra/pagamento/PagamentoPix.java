package br.com.locadora.infra.pagamento;

import br.com.locadora.contratos.MetodoPagamento;

import java.math.BigDecimal;
import java.util.UUID;

public class PagamentoPix implements MetodoPagamento {

    private final String chavePix;

    public PagamentoPix(String chavePix) {
        this.chavePix = chavePix;
    }

    @Override
    public boolean processar(BigDecimal valor) {
        String txid = UUID.randomUUID().toString().substring(0, 12);
        System.out.println("[PIX] Cobrança de R$ " + valor + " gerada para a chave " + chavePix + " (txid " + txid + ")");
        return true;
    }

    @Override
    public String getNome() {
        return "Pix";
    }
}
