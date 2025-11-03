package Loja.Loja_de_Jogos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal valor;
    private String descricao;
    private LocalDate dataLancamento;
    private String desenvolvedor;
    private String distribuidor;
    private String categoria;

    // requisitos
    private String so;
    private String armazenamento;
    private String processador;
    private String memoria;
    private String placaDeVideo;

    // Plataformas (ex.: PC, PS5, Xbox)
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "jogo_plataformas", joinColumns = @JoinColumn(name = "jogo_id"))
    @Column(name = "plataforma")
    @Enumerated(EnumType.STRING)
    private List<Plataforma> plataformas;

    @OneToMany(mappedBy = "jogo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Imagem> imagens;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "jogo_categoria",
            joinColumns = @JoinColumn(name = "jogo_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "jogo", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Chave> chaves;
}