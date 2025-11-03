package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Imagem;
import Loja.Loja_de_Jogos.dtos.ImagemDTO;
import Loja.Loja_de_Jogos.Services.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagens")
@CrossOrigin(origins = "*")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @GetMapping
    public List<ImagemDTO> listarTodas() {
        return imagemService.listarTodas().stream().map(ImagemDTO::new).toList();
    }

    @PostMapping
    public ResponseEntity<ImagemDTO> criar(@RequestBody Imagem imagem) {
        Imagem novaImagem = imagemService.salvar(imagem);
        return ResponseEntity.ok(new ImagemDTO(novaImagem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagemDTO> buscarPorId(@PathVariable Long id) {
        return imagemService.buscarPorId(id)
                .map(imagem -> ResponseEntity.ok(new ImagemDTO(imagem)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!imagemService.existePorId(id)) return ResponseEntity.notFound().build();
        imagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}