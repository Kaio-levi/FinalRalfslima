package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Carrinho;
import Loja.Loja_de_Jogos.Repositories.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {

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