package br.com.locadora.contratos;

import br.com.locadora.dominio.Veiculo;

import java.util.Optional;

public interface VeiculoRepository {

    void salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorPlaca(String placa);
}
