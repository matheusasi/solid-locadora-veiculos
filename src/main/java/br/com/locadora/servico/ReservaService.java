package br.com.locadora.servico;

import br.com.locadora.contratos.CalculadoraPreco;
import br.com.locadora.contratos.ReservaRepository;
import br.com.locadora.contratos.VeiculoRepository;
import br.com.locadora.dominio.Cliente;
import br.com.locadora.dominio.Reserva;
import br.com.locadora.dominio.Veiculo;

import java.time.LocalDate;

public class ReservaService {

    private final VeiculoRepository veiculoRepository;
    private final ReservaRepository reservaRepository;
    private final CalculadoraPreco calculadoraPreco;

    public ReservaService(VeiculoRepository veiculoRepository,
                          ReservaRepository reservaRepository,
                          CalculadoraPreco calculadoraPreco) {
        this.veiculoRepository = veiculoRepository;
        this.reservaRepository = reservaRepository;
        this.calculadoraPreco = calculadoraPreco;
    }

    public Reserva criarReserva(Cliente cliente, String placa, LocalDate inicio, LocalDate fim) {
        Veiculo veiculo = veiculoRepository.buscarPorPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado: " + placa));

        Reserva reserva = new Reserva(cliente, veiculo, inicio, fim);
        veiculo.reservar();
        reserva.definirValorTotal(calculadoraPreco.calcular(reserva));

        veiculoRepository.salvar(veiculo);
        reservaRepository.salvar(reserva);
        return reserva;
    }
}
