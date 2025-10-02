package Loja.Loja_de_Jogos.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.Data;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Carrinho carrinho;
    private Data datapedido;
    @Enumerated(EnumType.STRING)
    private Status status;
}
