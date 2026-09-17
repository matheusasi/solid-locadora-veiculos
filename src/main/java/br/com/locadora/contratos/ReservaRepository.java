package br.com.locadora.contratos;

import br.com.locadora.dominio.Reserva;

import java.util.Optional;

public interface ReservaRepository {

    void salvar(Reserva reserva);

    Optional<Reserva> buscarPorId(String id);
}
