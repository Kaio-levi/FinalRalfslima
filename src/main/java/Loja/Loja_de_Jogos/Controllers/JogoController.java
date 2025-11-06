package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Jogo;
import Loja.Loja_de_Jogos.dtos.JogoCreateDTO;
import Loja.Loja_de_Jogos.dtos.JogoDTO;
import Loja.Loja_de_Jogos.Repositories.JogoRepository;
import Loja.Loja_de_Jogos.Services.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
@CrossOrigin(origins = "*")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @GetMapping
    public ResponseEntity<Page<JogoDTO>> listarTodos(Pageable pageable) {
        Page<JogoDTO> jogosPaginados = jogoService.listarTodos(pageable).map(JogoDTO::new);
        return ResponseEntity.ok(jogosPaginados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogoDTO> buscarPorId(@PathVariable Long id) {
        return jogoService.buscarPorId(id)
                .map(jogo -> ResponseEntity.ok(new JogoDTO(jogo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JogoDTO> criar(@RequestBody JogoCreateDTO jogoDto) {
        Jogo novoJogo = jogoService.criarFromDto(jogoDto);
        return ResponseEntity.ok(new JogoDTO(novoJogo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogoDTO> atualizar(@PathVariable Long id, @RequestBody Jogo jogoAtualizado) {
        return jogoService.atualizar(id, jogoAtualizado)
                .map(jogo -> ResponseEntity.ok(new JogoDTO(jogo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!jogoService.existePorId(id)) { // Uso do Service
            return ResponseEntity.notFound().build();
        }
        jogoService.deletar(id); // Uso do Service
        return ResponseEntity.noContent().build();
    }
}