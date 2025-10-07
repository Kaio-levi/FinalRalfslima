package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Jogo;
import Loja.Loja_de_Jogos.Repositories.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
@CrossOrigin(origins = "*") // permite o front acessar
public class JogoController {

    @Autowired
    private JogoRepository jogoRepository;

    @GetMapping
    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarPorId(@PathVariable Long id) {
        return jogoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Jogo> criar(@RequestBody Jogo jogo) {
        Jogo novoJogo = jogoRepository.save(jogo);
        return ResponseEntity.ok(novoJogo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogo> atualizar(@PathVariable Long id, @RequestBody Jogo jogoAtualizado) {
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
            return ResponseEntity.ok(jogoRepository.save(jogo));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!jogoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jogoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}