package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Chave;
import Loja.Loja_de_Jogos.Services.ChaveService;
import Loja.Loja_de_Jogos.dtos.ChaveDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chaves")
@CrossOrigin(origins = "*")
public class ChaveController {

    @Autowired
    private ChaveService chaveService;

    @GetMapping
    public List<ChaveDTO> listarTodas() {
        return chaveService.listarTodas().stream().map(ChaveDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChaveDTO> buscarPorId(@PathVariable Long id) {
        return chaveService.buscarPorId(id)
                .map(chave -> ResponseEntity.ok(new ChaveDTO(chave)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChaveDTO> criar(@RequestBody Chave chave) {
        Chave novaChave = chaveService.criar(chave);
        return ResponseEntity.ok(new ChaveDTO(novaChave));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!chaveService.existePorId(id)) return ResponseEntity.notFound().build();
        chaveService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}