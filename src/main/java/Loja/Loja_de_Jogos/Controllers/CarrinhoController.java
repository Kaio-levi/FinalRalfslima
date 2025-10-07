package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Carrinho;
import Loja.Loja_de_Jogos.Repositories.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin(origins = "*")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @GetMapping
    public List<Carrinho> listarTodos() {
        return carrinhoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Carrinho> criar(@RequestBody Carrinho carrinho) {
        return ResponseEntity.ok(carrinhoRepository.save(carrinho));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!carrinhoRepository.existsById(id)) return ResponseEntity.notFound().build();
        carrinhoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}