package br.com.locadora.infra.pagamento;

import br.com.locadora.contratos.MetodoPagamento;

import java.math.BigDecimal;

public class PagamentoCartaoCredito implements MetodoPagamento {

    private final String finalCartao;
    private final BigDecimal limiteDisponivel;
    private final int parcelas;

    public PagamentoCartaoCredito(String finalCartao, BigDecimal limiteDisponivel, int parcelas) {
        if (parcelas < 1) {
            throw new IllegalArgumentException("Número de parcelas deve ser pelo menos 1");
        }
        this.finalCartao = finalCartao;
        this.limiteDisponivel = limiteDisponivel;
        this.parcelas = parcelas;
    }

    @Override
    public boolean processar(BigDecimal valor) {
        boolean aprovado = valor.compareTo(limiteDisponivel) <= 0;
        String resultado = aprovado ? "aprovado" : "recusado por limite insuficiente";
        System.out.println("[CARTAO] Final " + finalCartao + ", " + parcelas + "x, valor R$ " + valor + " " + resultado);
        return aprovado;
    }

    @Override
    public String getNome() {
        return "Cartão de Crédito";
    }
}
