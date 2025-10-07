package Loja.Loja_de_Jogos.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Carrinho carrinho;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    private LocalDate datapedido;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
