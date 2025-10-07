package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Key;
import Loja.Loja_de_Jogos.Repositories.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KeyService {

    @Autowired
    private KeyRepository keyRepository;

    public List<Key> listarTodas() {
        return keyRepository.findAll();
    }

    public Optional<Key> buscarPorId(Long id) {
        return keyRepository.findById(id);
    }

    public Key salvar(Key key) {
        return keyRepository.save(key);
    }

    public boolean deletar(Long id) {
        if (keyRepository.existsById(id)) {
            keyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
