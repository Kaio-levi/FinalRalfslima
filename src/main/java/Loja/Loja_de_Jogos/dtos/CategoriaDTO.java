package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Categoria;

public class CategoriaDTO {
    public Long id;
    public String nome;
    public String descricao;
    public String imagemUrl;
    public boolean isActive;

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
        this.imagemUrl = categoria.getImagemUrl();
        this.isActive = categoria.isActive();
    }
}
