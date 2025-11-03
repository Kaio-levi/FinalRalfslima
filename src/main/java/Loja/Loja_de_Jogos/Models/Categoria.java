package Loja.Loja_de_Jogos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String imagemUrl;
    private boolean isActive = true;

    @JsonIgnore // evita recursão infinita na serialização JSON
    @ManyToMany(mappedBy = "categorias")
    private List<Jogo> jogos;
}
