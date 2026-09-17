package br.com.locadora.infra.repositorio;

import br.com.locadora.contratos.VeiculoRepository;
import br.com.locadora.dominio.Veiculo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VeiculoRepositoryEmMemoria implements VeiculoRepository {

    private final Map<String, Veiculo> veiculos = new HashMap<>();

    @Override
    public void salvar(Veiculo veiculo) {
        veiculos.put(veiculo.getPlaca(), veiculo);
    }

    @Override
    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return Optional.ofNullable(veiculos.get(placa));
    }
}
