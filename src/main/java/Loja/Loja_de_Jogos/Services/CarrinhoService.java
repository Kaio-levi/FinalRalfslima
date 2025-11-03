package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Carrinho;
import Loja.Loja_de_Jogos.Models.CarrinhoJogo;
import Loja.Loja_de_Jogos.Models.CarrinhoJogoId;
import Loja.Loja_de_Jogos.Repositories.CarrinhoJogoRepository;
import Loja.Loja_de_Jogos.Repositories.CarrinhoRepository;
import Loja.Loja_de_Jogos.Repositories.JogoRepository;
import Loja.Loja_de_Jogos.dtos.AddToCartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {
    @Autowired
    private CarrinhoJogoRepository carrinhoJogoRepository;

    @Autowired
    private JogoRepository jogoRepository;
    public Carrinho adicionarItem(AddToCartDTO dto) {
        // Exemplo: busca o carrinho do usuário (aqui assume id=1, ajuste para autenticação real)
        Carrinho carrinho = carrinhoRepository.findById(1L).orElseGet(() -> {
            Carrinho novo = new Carrinho();
            // inicialize usuário, status, etc. conforme necessário
            return carrinhoRepository.save(novo);
        });

        // Adiciona ou atualiza o item no carrinho
        boolean found = false;
        if (carrinho.getCarrinhoJogo() != null) {
            for (var item : carrinho.getCarrinhoJogo()) {
                if (item.getCarrinhoJogoId().getIdJogo().equals(dto.gameId)) {
                    item.setQuantidade(item.getQuantidade() + dto.quantity);
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            var jogo = jogoRepository.findById(dto.gameId).orElseThrow();
            var carrinhoJogoId = new CarrinhoJogoId(carrinho.getId(), dto.gameId);
            var novoItem = new CarrinhoJogo();
            novoItem.setCarrinhoJogoId(carrinhoJogoId);
            novoItem.setCarrinho(carrinho);
            novoItem.setJogo(jogo);
            novoItem.setQuantidade(dto.quantity);
            carrinho.getCarrinhoJogo().add(novoItem);
            carrinhoJogoRepository.save(novoItem);
        }
        return carrinhoRepository.save(carrinho);
    }
    public boolean existePorId(Long id) {
        return carrinhoRepository.existsById(id);
    }

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public List<Carrinho> listarTodos() {
        return carrinhoRepository.findAll();
    }

    public Optional<Carrinho> buscarPorId(Long id) {
        return carrinhoRepository.findById(id);
    }

    public Carrinho salvar(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }

    public boolean deletar(Long id) {
        if (carrinhoRepository.existsById(id)) {
            carrinhoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}