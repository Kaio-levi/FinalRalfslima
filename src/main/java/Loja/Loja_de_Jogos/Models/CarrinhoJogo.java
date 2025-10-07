package Loja.Loja_de_Jogos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name= "carrinho_jogo")
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoJogo {

    @EmbeddedId
    private CarrinhoJogoId carrinhoJogoId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCarrinho",insertable = false,updatable = false)
    private Carrinho carrinho;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idJogo",insertable = false,updatable = false)
    private Jogo jogo;

    @Column(name = "quantidade")
    private Integer quantidade;


}
