package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Chave;
import Loja.Loja_de_Jogos.Repositories.ChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChaveService {
    @Autowired
    private ChaveRepository chaveRepository;

    public List<Chave> listarTodas() {
        return chaveRepository.findAll();
    }

    public Optional<Chave> buscarPorId(Long id) {
        return chaveRepository.findById(id);
    }

    public Chave criar(Chave chave) {
        return chaveRepository.save(chave);
    }

    public boolean existePorId(Long id) {
        return chaveRepository.existsById(id);
    }

    public void deletar(Long id) {
        chaveRepository.deleteById(id);
    }
}
