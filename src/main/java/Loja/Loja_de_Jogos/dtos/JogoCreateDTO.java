package Loja.Loja_de_Jogos.dtos;

import java.math.BigDecimal;
import java.util.List;

public class JogoCreateDTO {
    public String nome;
    public BigDecimal valor;
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
    public List<ImagemCreateDTO> imagens;
    public List<CategoriaIdDTO> categorias;
}
