package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Jogo;
import Loja.Loja_de_Jogos.Models.Imagem;
import java.math.BigDecimal;
import java.util.List;

public class PedidoItemDTO {
    public Long jogoId;
    public String nomeJogo;
    public Integer quantidade;
    public BigDecimal valor; // preço unitário no momento do pedido
    public String imagemUrl; // url da imagem principal do jogo (se disponível)

    public PedidoItemDTO(Long jogoId, String nomeJogo, Integer quantidade) {
        this.jogoId = jogoId;
        this.nomeJogo = nomeJogo;
        this.quantidade = quantidade;
        this.valor = null;
        this.imagemUrl = null;
    }

    public PedidoItemDTO(Jogo jogo, Integer quantidade) {
        this.jogoId = jogo.getId();
        this.nomeJogo = jogo.getNome();
        this.quantidade = quantidade;
        this.valor = jogo.getValor();
        // tentar pegar a imagem principal, se existir
        this.imagemUrl = null;
        List<Imagem> imgs = jogo.getImagens();
        if (imgs != null && !imgs.isEmpty()) {
            // preferir a primeira imagem marcada como principal
            for (Imagem img : imgs) {
                if (img.isMainImage()) {
                    this.imagemUrl = img.getUrl();
                    break;
                }
            }
            if (this.imagemUrl == null) {
                this.imagemUrl = imgs.get(0).getUrl();
            }
        }
    }
}
