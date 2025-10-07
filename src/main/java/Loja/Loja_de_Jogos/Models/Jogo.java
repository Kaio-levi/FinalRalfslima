package Loja.Loja_de_Jogos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long valor;
    private String descricao;
    private LocalDate dataLancamento;
    private String desenvolvedor;
    private String distribuidor;

    private String SO;
    private String Armazenamento;
    private String processador;
    private String memoria;
    private String placaDeVideo;


    @Enumerated(EnumType.ORDINAL)
    private List<Plataforma> plataformas;

    @OneToMany(mappedBy = "imagem", fetch = FetchType.LAZY)
    private List<Imagem> imagens;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "jogo_categoria",
            joinColumns = @JoinColumn(name = "jogo_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "jogo", fetch = FetchType.LAZY)
    private List<Key> keys;

}
