package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Imagem;
import Loja.Loja_de_Jogos.Repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    public List<Imagem> listarTodas() {
        return imagemRepository.findAll();
    }

    public Optional<Imagem> buscarPorId(Long id) {
        return imagemRepository.findById(id);
    }

    public Imagem salvar(Imagem imagem) {
        return imagemRepository.save(imagem);
    }

    public boolean deletar(Long id) {
        if (imagemRepository.existsById(id)) {
            imagemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}