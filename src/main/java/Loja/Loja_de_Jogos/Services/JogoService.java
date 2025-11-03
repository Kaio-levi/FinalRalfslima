package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Jogo;
import Loja.Loja_de_Jogos.Repositories.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JogoService {

    @Autowired
    private JogoRepository jogoRepository;

    public Page<Jogo> listarTodos(Pageable pageable) {
        return jogoRepository.findAll(pageable);
    }

    public Optional<Jogo> buscarPorId(Long id) {
        return jogoRepository.findById(id);
    }

    public Jogo criar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    public Optional<Jogo> atualizar(Long id, Jogo jogoAtualizado) {
        return jogoRepository.findById(id).map(jogoExistente -> {
            // Lógica de atualização
            jogoExistente.setNome(jogoAtualizado.getNome());
            jogoExistente.setValor(jogoAtualizado.getValor());
            jogoExistente.setDescricao(jogoAtualizado.getDescricao());
            jogoExistente.setDataLancamento(jogoAtualizado.getDataLancamento());
            jogoExistente.setDesenvolvedor(jogoAtualizado.getDesenvolvedor());
            jogoExistente.setDistribuidor(jogoAtualizado.getDistribuidor());
            jogoExistente.setSo(jogoAtualizado.getSo());
            jogoExistente.setArmazenamento(jogoAtualizado.getArmazenamento());
            jogoExistente.setProcessador(jogoAtualizado.getProcessador());
            jogoExistente.setMemoria(jogoAtualizado.getMemoria());
            jogoExistente.setPlacaDeVideo(jogoAtualizado.getPlacaDeVideo());
            jogoExistente.setPlataformas(jogoAtualizado.getPlataformas());
            jogoExistente.setCategorias(jogoAtualizado.getCategorias());

            return jogoRepository.save(jogoExistente);
        });
    }
    public void deletar(Long id) {
        jogoRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return jogoRepository.existsById(id);
    }
}
