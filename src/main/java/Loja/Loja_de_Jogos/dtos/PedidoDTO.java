package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Pedido;
import Loja.Loja_de_Jogos.Models.PedidoItem;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDTO {
    public Long id;
    public Long usuarioId;
    public String dataPedido;
    public String status;
    public List<PedidoItemDTO> itens;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.usuarioId = pedido.getUsuario() != null ? pedido.getUsuario().getId() : null;
        this.dataPedido = pedido.getDatapedido() != null ? pedido.getDatapedido().toString() : null;
        this.status = pedido.getStatus() != null ? pedido.getStatus().name() : null;
        this.itens = pedido.getItens() != null ? pedido.getItens().stream().map(
            item -> new PedidoItemDTO(item.getJogo(), item.getQuantidade())
        ).collect(Collectors.toList()) : null;
    }
}
