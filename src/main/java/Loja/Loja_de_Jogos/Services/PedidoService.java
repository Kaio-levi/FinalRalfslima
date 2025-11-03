package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Pedido;
import Loja.Loja_de_Jogos.Repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    public Optional<Pedido> atualizar(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id).map(pedidoExistente -> {
            // Adicione lógica de atualização conforme necessário
            // Exemplo: pedidoExistente.setStatus(pedidoAtualizado.getStatus());
            return pedidoRepository.save(pedidoExistente);
        });
    }

    public boolean existePorId(Long id) {
        return pedidoRepository.existsById(id);
    }

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public boolean deletar(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}