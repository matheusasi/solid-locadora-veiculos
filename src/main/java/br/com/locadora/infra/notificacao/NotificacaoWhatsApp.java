package br.com.locadora.infra.notificacao;

import br.com.locadora.contratos.CanalNotificacao;
import br.com.locadora.dominio.Cliente;

public class NotificacaoWhatsApp implements CanalNotificacao {

    @Override
    public void enviar(Cliente cliente, String mensagem) {
        System.out.println("[WHATSAPP] Para: " + cliente.getTelefone() + " | " + mensagem);
    }
}
