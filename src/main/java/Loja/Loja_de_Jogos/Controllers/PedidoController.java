package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.*;
import Loja.Loja_de_Jogos.dtos.PedidoDTO;
import Loja.Loja_de_Jogos.dtos.PedidoCreateDTO;
import Loja.Loja_de_Jogos.dtos.PedidoCreateDTO.ItemDTO;
import Loja.Loja_de_Jogos.Services.PedidoService;
import Loja.Loja_de_Jogos.Repositories.JogoRepository;
import Loja.Loja_de_Jogos.Repositories.UsuarioRepository;
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

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoCreateDTO pedidoCreateDTO) {
    Pedido pedido = new Pedido();
    pedido.setDatapedido(java.time.LocalDate.now());
    pedido.setStatus(Status.PENDENTE); // status inicial: aguardando pagamento

        // Buscar usuário
        Usuario usuario = usuarioRepository.findById(pedidoCreateDTO.usuarioId).orElse(null);
        pedido.setUsuario(usuario);

        // Criar itens do pedido
        java.util.List<PedidoItem> itens = new java.util.ArrayList<>();
        if (pedidoCreateDTO.itens != null) {
            for (ItemDTO itemDTO : pedidoCreateDTO.itens) {
                Jogo jogo = jogoRepository.findById(itemDTO.jogoId).orElse(null);
                if (jogo != null && itemDTO.quantidade != null && itemDTO.quantidade > 0) {
                    PedidoItem item = new PedidoItem();
                    item.setJogo(jogo);
                    item.setQuantidade(itemDTO.quantidade);
                    item.setPedido(pedido);
                    itens.add(item);
                }
            }
        }
        pedido.setItens(itens);

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