package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Cargo;
import Loja.Loja_de_Jogos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@CrossOrigin(origins = "*")
public class CargoController {

    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping
    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> buscarPorId(@PathVariable Long id) {
        return cargoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cargo criar(@RequestBody Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cargo> atualizar(@PathVariable Long id, @RequestBody Cargo cargoAtualizado) {
        return cargoRepository.findById(id).map(cargo -> {
            cargo.setNome(cargoAtualizado.getNome());
            return ResponseEntity.ok(cargoRepository.save(cargo));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!cargoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cargoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
