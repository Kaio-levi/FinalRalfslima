package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Chave;
import Loja.Loja_de_Jogos.Repositories.ChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KeyService {

    @Autowired
    private ChaveRepository chaveRepository;

    public List<Chave> listarTodas() {
        return chaveRepository.findAll();
    }

    public Optional<Chave> buscarPorId(Long id) {
        return chaveRepository.findById(id);
    }

    public Chave salvar(Chave chave) {
        return chaveRepository.save(chave);
    }

    public boolean deletar(Long id) {
        if (chaveRepository.existsById(id)) {
            chaveRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
