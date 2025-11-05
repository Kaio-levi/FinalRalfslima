package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Jogo;

public class PedidoItemDTO {
    public Long jogoId;
    public String nomeJogo;
    public Integer quantidade;

    public PedidoItemDTO(Long jogoId, String nomeJogo, Integer quantidade) {
        this.jogoId = jogoId;
        this.nomeJogo = nomeJogo;
        this.quantidade = quantidade;
    }

    public PedidoItemDTO(Jogo jogo, Integer quantidade) {
        this.jogoId = jogo.getId();
        this.nomeJogo = jogo.getNome();
        this.quantidade = quantidade;
    }
}
