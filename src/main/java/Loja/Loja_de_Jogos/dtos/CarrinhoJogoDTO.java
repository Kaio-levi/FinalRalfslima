package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.CarrinhoJogo;

public class CarrinhoJogoDTO {
    public Long carrinhoId;
    public Long jogoId;
    public Integer quantidade;

    public CarrinhoJogoDTO(CarrinhoJogo carrinhoJogo) {
        this.carrinhoId = carrinhoJogo.getCarrinhoJogoId().getIdCarrinho();
        this.jogoId = carrinhoJogo.getCarrinhoJogoId().getIdJogo();
        this.quantidade = carrinhoJogo.getQuantidade();
    }
}
