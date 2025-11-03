package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Carrinho;
import Loja.Loja_de_Jogos.Models.CarrinhoJogo;

import java.util.List;
import java.util.stream.Collectors;

public class CarrinhoDTO {
    public Long id;
    public Long usuarioId;
    public List<CarrinhoJogoDTO> carrinhoJogos;
    public String status;

    public CarrinhoDTO(Carrinho carrinho) {
        this.id = carrinho.getId();
        this.usuarioId = carrinho.getUsuario() != null ? carrinho.getUsuario().getId() : null;
        this.carrinhoJogos = carrinho.getCarrinhoJogo() != null ? carrinho.getCarrinhoJogo().stream().map(CarrinhoJogoDTO::new).collect(Collectors.toList()) : null;
        this.status = carrinho.getStatus() != null ? carrinho.getStatus().name() : null;
    }
}
