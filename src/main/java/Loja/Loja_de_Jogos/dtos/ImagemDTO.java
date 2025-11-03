package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Imagem;

public class ImagemDTO {
    public Long id;
    public String nome;
    public String url;
    public String altText;
    public boolean isMainImage;

    public ImagemDTO(Imagem imagem) {
        this.id = imagem.getId();
        this.nome = imagem.getNome();
    this.url = imagem.getUrl();
    this.altText = imagem.getAltText();
    this.isMainImage = imagem.isMainImage();
    }
}
