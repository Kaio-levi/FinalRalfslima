package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Pedido;
import Loja.Loja_de_Jogos.dtos.PedidoDTO;
import Loja.Loja_de_Jogos.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> listarTodos() {
        return pedidoService.listarTodos().stream().map(PedidoDTO::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(pedido -> ResponseEntity.ok(new PedidoDTO(pedido)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.salvar(pedido);
        return ResponseEntity.ok(new PedidoDTO(novoPedido));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizar(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        return pedidoService.atualizar(id, pedidoAtualizado)
                .map(pedido -> ResponseEntity.ok(new PedidoDTO(pedido)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!pedidoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}