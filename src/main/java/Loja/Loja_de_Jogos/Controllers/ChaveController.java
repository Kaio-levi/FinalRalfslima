package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Chave;
import Loja.Loja_de_Jogos.Repositories.ChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keys")
@CrossOrigin(origins = "*")
public class ChaveController {

    @Autowired
    private ChaveRepository chaveRepository;

    @GetMapping
    public List<Chave> listarTodas() {
        return chaveRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chave> buscarPorId(@PathVariable Long id) {
        return chaveRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Chave> criar(@RequestBody Chave chave) {
        return ResponseEntity.ok(chaveRepository.save(chave));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!chaveRepository.existsById(id)) return ResponseEntity.notFound().build();
        chaveRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}