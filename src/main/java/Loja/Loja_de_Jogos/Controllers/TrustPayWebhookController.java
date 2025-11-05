package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Pedido;
import Loja.Loja_de_Jogos.Models.Status;
import Loja.Loja_de_Jogos.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/webhooks/trustpay")
@CrossOrigin(origins = "*")
public class TrustPayWebhookController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> receberWebhook(@RequestBody Map<String, Object> webhook) {
        try {
            // Exemplo: webhook contém { intentId, status, orderId, ... }
            String status = (String) webhook.get("status");
            String orderId = (String) webhook.get("orderId");
            if (orderId != null && status != null) {
                // Supondo que orderId é o id do pedido salvo
                Long pedidoId = null;
                try { pedidoId = Long.parseLong(orderId.replaceAll("[^0-9]", "")); } catch (Exception ignored) {}
                if (pedidoId != null) {
                    Pedido pedido = pedidoService.buscarPorId(pedidoId).orElse(null);
                    if (pedido != null) {
                        if ("APPROVED".equalsIgnoreCase(status) || "AUTHORIZED".equalsIgnoreCase(status)) {
                            pedido.setStatus(Status.APROVADO);
                        } else if ("PENDING".equalsIgnoreCase(status)) {
                            pedido.setStatus(Status.PENDENTE);
                        } else if ("REJECTED".equalsIgnoreCase(status) || "FAILED".equalsIgnoreCase(status)) {
                            pedido.setStatus(Status.RECUSADO);
                        }
                        pedidoService.salvar(pedido);
                    }
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar webhook: " + e.getMessage());
        }
    }
}
