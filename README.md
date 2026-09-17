# Locadora de Veículos com SOLID

Aplicação em Java puro que simula o fluxo de uma locadora de veículos: criação de reserva, cálculo do valor com descontos, pagamento e notificação do cliente. O objetivo é aplicar os cinco princípios do SOLID.

## Como executar

Requisito: Java 17 ou superior.

Com Maven:

```bash
mvn compile exec:java
```

Sem Maven:

```bash
javac -d out $(find src/main/java -name "*.java")
java -cp out br.com.locadora.Main
```

## Estrutura

```
src/main/java/br/com/locadora
├── Main.java                 montagem das dependências e cenários
├── dominio                   entidades (Cliente, Veiculo, Reserva, Pagamento)
├── contratos                 interfaces
├── infra
│   ├── desconto              regras de desconto
│   ├── notificacao           e-mail e WhatsApp
│   ├── pagamento             Pix e cartão de crédito
│   └── repositorio           repositórios em memória
└── servico                   ReservaService, PagamentoService e calculadora de preço
```

## Cenários executados no Main

1. Reserva de 8 dias para cliente fidelidade, paga com Pix e notificada por WhatsApp (descontos de 10% e 5%).
2. Reserva de 3 dias paga com cartão de crédito e notificada por e-mail.
3. Reserva paga com cartão sem limite suficiente: pagamento recusado, reserva cancelada e veículo liberado.

## Aplicação dos princípios

**SRP**: as entidades do pacote `dominio` guardam apenas seus dados e regras de integridade (CPF válido, datas coerentes, transições de status da reserva). Cálculo de preço, pagamento, notificação e persistência ficam em classes separadas.

**OCP**: para criar um novo desconto, forma de pagamento ou canal de notificação basta criar uma classe que implemente `RegraDesconto`, `MetodoPagamento` ou `CanalNotificacao` e passá-la no `Main`. Os serviços não precisam ser alterados e não existe `switch` ou `instanceof` para escolher comportamento.

**LSP**: qualquer implementação pode substituir outra sem quebrar o serviço. `PagamentoPix` e `PagamentoCartaoCredito` sempre retornam se o pagamento foi aprovado; as regras de desconto retornam zero quando não se aplicam, em vez de lançar exceção.

**ISP**: as interfaces são pequenas, com um ou dois métodos cada. Um canal de notificação só precisa saber enviar mensagem, sem depender de métodos de pagamento ou persistência.

**DIP**: `ReservaService` e `PagamentoService` dependem apenas de interfaces recebidas pelo construtor. Os objetos concretos são criados somente no `Main`.

## Exemplo de extensão

Para adicionar pagamento por boleto, basta criar a classe abaixo e usá-la no `Main`:

```java
public class PagamentoBoleto implements MetodoPagamento {

    @Override
    public boolean processar(BigDecimal valor) {
        System.out.println("[BOLETO] Boleto de R$ " + valor + " emitido");
        return true;
    }

    @Override
    public String getNome() {
        return "Boleto";
    }
}
```
