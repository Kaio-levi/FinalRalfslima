package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Pedido;

public class PedidoDTO {
    public Long id;
    public Long carrinhoId;
    public Long usuarioId;
    public String dataPedido;
    public String status;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.carrinhoId = pedido.getCarrinho() != null ? pedido.getCarrinho().getId() : null;
        this.usuarioId = pedido.getUsuario() != null ? pedido.getUsuario().getId() : null;
        this.dataPedido = pedido.getDatapedido() != null ? pedido.getDatapedido().toString() : null;
        this.status = pedido.getStatus() != null ? pedido.getStatus().name() : null;
    }
}
