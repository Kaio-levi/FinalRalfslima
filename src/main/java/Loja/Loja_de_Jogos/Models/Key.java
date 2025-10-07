package Loja.Loja_de_Jogos.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Key {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long codigo;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Jogo jogo;
}
