package br.com.locadora.contratos;

import br.com.locadora.dominio.Cliente;

public interface CanalNotificacao {

    void enviar(Cliente cliente, String mensagem);
}
