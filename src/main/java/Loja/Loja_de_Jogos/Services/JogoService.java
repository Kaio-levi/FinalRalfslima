package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Jogo;
import Loja.Loja_de_Jogos.Repositories.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }

    public Optional<Jogo> buscarPorId(Long id) {
        return jogoRepository.findById(id);
    }

    public Jogo salvar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public Optional<Jogo> atualizar(Long id, Jogo jogoAtualizado) {
        return jogoRepository.findById(id).map(jogo -> {
            jogo.setNome(jogoAtualizado.getNome());
            jogo.setValor(jogoAtualizado.getValor());
            jogo.setDescricao(jogoAtualizado.getDescricao());
            jogo.setDataLancamento(jogoAtualizado.getDataLancamento());
            jogo.setDesenvolvedor(jogoAtualizado.getDesenvolvedor());
            jogo.setDistribuidor(jogoAtualizado.getDistribuidor());
            jogo.setSo(jogoAtualizado.getSo());
            jogo.setArmazenamento(jogoAtualizado.getArmazenamento());
            jogo.setProcessador(jogoAtualizado.getProcessador());
            jogo.setMemoria(jogoAtualizado.getMemoria());
            jogo.setPlacaDeVideo(jogoAtualizado.getPlacaDeVideo());
            jogo.setPlataformas(jogoAtualizado.getPlataformas());
            jogo.setCategorias(jogoAtualizado.getCategorias());
            return jogoRepository.save(jogo);
        });
    }

    public boolean deletar(Long id) {
        if (!jogoRepository.existsById(id)) {
            return false;
        }
        jogoRepository.deleteById(id);
        return true;
    }
}
