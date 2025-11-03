package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Categoria;
import Loja.Loja_de_Jogos.dtos.CategoriaDTO;
import Loja.Loja_de_Jogos.Services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> listarTodas() {
        return categoriaService.listarTodos().stream().map(CategoriaDTO::new).toList();
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaService.salvar(categoria);
        return ResponseEntity.ok(new CategoriaDTO(novaCategoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        return categoriaService.buscarPorId(id)
                .map(categoria -> ResponseEntity.ok(new CategoriaDTO(categoria)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!categoriaService.existePorId(id)) return ResponseEntity.notFound().build();
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}