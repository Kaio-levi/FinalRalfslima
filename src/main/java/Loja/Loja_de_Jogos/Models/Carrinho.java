package Loja.Loja_de_Jogos.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Carrinho {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idUsuario;
}
