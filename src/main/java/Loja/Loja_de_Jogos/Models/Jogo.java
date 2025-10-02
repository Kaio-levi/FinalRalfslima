package Loja.Loja_de_Jogos.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long valor;
    private String descricao;
    private Data dataLancamento;
    private String desenvolvedor;
    private String distribuidor;
    private List<Imagem> imagens;
    @Enumerated(EnumType.STRING)
    private List<Plaforma> plataformas;

}
