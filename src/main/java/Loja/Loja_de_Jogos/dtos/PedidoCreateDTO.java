package Loja.Loja_de_Jogos.dtos;

import java.util.List;

public class PedidoCreateDTO {
    public Long usuarioId;
    public List<ItemDTO> itens;

    public static class ItemDTO {
        public Long jogoId;
        public Integer quantidade;
    }
}
