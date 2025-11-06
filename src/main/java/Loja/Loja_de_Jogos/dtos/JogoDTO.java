package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Categoria;
import Loja.Loja_de_Jogos.Models.Imagem;
import Loja.Loja_de_Jogos.Models.Jogo;
import Loja.Loja_de_Jogos.Models.Plataforma;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class JogoDTO {
    public Long id;
    public String nome;
    public double valor;
    public String descricao;
    public String dataLancamento;
    public String desenvolvedor;
    public String distribuidor;
    public String categoria;
    public String so;
    public String armazenamento;
    public String processador;
    public String memoria;
    public String placaDeVideo;
    public List<String> plataformas;
    public List<ImagemDTO> imagens;
    public List<CategoriaDTO> categorias;
    // chaves removed

    public JogoDTO(Jogo jogo) {
        this.id = jogo.getId();
        this.nome = jogo.getNome();
        this.valor = jogo.getValor() != null ? jogo.getValor().doubleValue() : 0.0;
        this.descricao = jogo.getDescricao();
        this.dataLancamento = jogo.getDataLancamento() != null ? jogo.getDataLancamento().toString() : null;
        this.desenvolvedor = jogo.getDesenvolvedor();
        this.distribuidor = jogo.getDistribuidor();
        this.categoria = jogo.getCategoria();
        this.so = jogo.getSo();
        this.armazenamento = jogo.getArmazenamento();
        this.processador = jogo.getProcessador();
        this.memoria = jogo.getMemoria();
        this.placaDeVideo = jogo.getPlacaDeVideo();
        this.plataformas = jogo.getPlataformas() != null ? jogo.getPlataformas().stream().map(Plataforma::name).collect(Collectors.toList()) : null;
        this.imagens = jogo.getImagens() != null ? jogo.getImagens().stream().map(ImagemDTO::new).collect(Collectors.toList()) : null;
    this.categorias = jogo.getCategorias() != null ? jogo.getCategorias().stream().map(CategoriaDTO::new).collect(Collectors.toList()) : null;
    }
}
