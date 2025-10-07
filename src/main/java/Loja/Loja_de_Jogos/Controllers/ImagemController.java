package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Imagem;
import Loja.Loja_de_Jogos.Repositories.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagens")
@CrossOrigin(origins = "*")
public class ImagemController {

    @Autowired
    private ImagemRepository imagemRepository;

    @GetMapping
    public List<Imagem> listarTodas() {
        return imagemRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Imagem> criar(@RequestBody Imagem imagem) {
        return ResponseEntity.ok(imagemRepository.save(imagem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!imagemRepository.existsById(id)) return ResponseEntity.notFound().build();
        imagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}