package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Carrinho;
import Loja.Loja_de_Jogos.dtos.AddToCartDTO;
import Loja.Loja_de_Jogos.dtos.CarrinhoDTO;
import Loja.Loja_de_Jogos.Services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin(origins = "*")
public class CarrinhoController {
    @PostMapping("/items")
    public ResponseEntity<CarrinhoDTO> adicionarItem(@RequestBody AddToCartDTO dto) {
        Carrinho carrinhoAtualizado = carrinhoService.adicionarItem(dto);
        return ResponseEntity.ok(new CarrinhoDTO(carrinhoAtualizado));
    }

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public List<CarrinhoDTO> listarTodos() {
        return carrinhoService.listarTodos().stream().map(CarrinhoDTO::new).toList();
    }

    @PostMapping
    public ResponseEntity<CarrinhoDTO> criar(@RequestBody Carrinho carrinho) {
        Carrinho novoCarrinho = carrinhoService.salvar(carrinho);
        return ResponseEntity.ok(new CarrinhoDTO(novoCarrinho));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> buscarPorId(@PathVariable Long id) {
        return carrinhoService.buscarPorId(id)
                .map(carrinho -> ResponseEntity.ok(new CarrinhoDTO(carrinho)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!carrinhoService.existePorId(id)) return ResponseEntity.notFound().build();
        carrinhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}