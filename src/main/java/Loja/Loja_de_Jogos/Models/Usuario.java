package Loja.Loja_de_Jogos.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Data datacriacao;
    private List<Pedido> pedidos;
}
