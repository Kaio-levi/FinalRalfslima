package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Key;
import Loja.Loja_de_Jogos.Repositories.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keys")
@CrossOrigin(origins = "*")
public class KeyController {

    @Autowired
    private KeyRepository keyRepository;

    @GetMapping
    public List<Key> listarTodas() {
        return keyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Key> buscarPorId(@PathVariable Long id) {
        return keyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Key> criar(@RequestBody Key key) {
        return ResponseEntity.ok(keyRepository.save(key));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!keyRepository.existsById(id)) return ResponseEntity.notFound().build();
        keyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}