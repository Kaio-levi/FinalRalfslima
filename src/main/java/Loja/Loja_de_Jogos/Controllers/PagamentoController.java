package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Status;
import Loja.Loja_de_Jogos.Services.PagamentoService;
import Loja.Loja_de_Jogos.Models.Pedido;
import Loja.Loja_de_Jogos.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/pagamentos")
@CrossOrigin(origins = "*")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private PedidoService pedidoService;


    // POST /api/merchant/v1/payment-intents
    @PostMapping("/merchant/v1/payment-intents")
    public ResponseEntity<?> criarIntent(@RequestBody Map<String, Object> payload) {
        System.out.println("[LOG] [INTENT] Recebida requisição de criação de intent: " + payload);
        try {
            Map<String, Object> resposta = pagamentoService.criarIntentPagamento(payload);
            System.out.println("[LOG] [INTENT] Resposta da criação de intent: " + resposta);
            // Se resposta contém "id", retorne direto, senão se contém "data", retorne data, senão resposta completa
            if (resposta != null && resposta.containsKey("id")) {
                return ResponseEntity.ok(resposta);
            } else if (resposta != null && resposta.containsKey("data")) {
                Object data = resposta.get("data");
                if (data instanceof Map && ((Map<?, ?>) data).containsKey("id")) {
                    return ResponseEntity.ok(data);
                }
            }
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            System.out.println("[LOG] [INTENT] Erro ao criar intent: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", Map.of("success", false, "message", e.getMessage())));
        }
    }

    // POST /api/merchant/v1/payment-intents/{intentId}/capture
    @PostMapping("/merchant/v1/payment-intents/{intentId}/capture")
    public ResponseEntity<?> capturar(@PathVariable String intentId, @RequestBody Map<String, Object> payload) {
        try {
            Map<String, Object> resposta = pagamentoService.capturarPagamento(intentId, payload);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", Map.of("success", false, "message", e.getMessage())));
        }
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<?> status(@PathVariable String paymentId) {
        Map<String, Object> resposta = pagamentoService.consultarStatus(paymentId);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/finalizar/{pedidoId}")
    public ResponseEntity<?> finalizarPedido(@PathVariable Long pedidoId, @RequestBody Map<String, Object> pagamentoInfo) {
        Pedido pedido = pedidoService.buscarPorId(pedidoId).orElse(null);
        if (pedido == null) return ResponseEntity.notFound().build();
        pedido.setStatus(Status.APROVADO);
        pedidoService.salvar(pedido);
        return ResponseEntity.ok("Pedido finalizado com sucesso!");
    }
}
