package br.com.locadora.infra.repositorio;

import br.com.locadora.contratos.ReservaRepository;
import br.com.locadora.dominio.Reserva;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReservaRepositoryEmMemoria implements ReservaRepository {

    private final Map<String, Reserva> reservas = new HashMap<>();

    @Override
    public void salvar(Reserva reserva) {
        reservas.put(reserva.getId(), reserva);
    }

    @Override
    public Optional<Reserva> buscarPorId(String id) {
        return Optional.ofNullable(reservas.get(id));
    }
}
