package Loja.Loja_de_Jogos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class CarrinhoJogoId implements Serializable {

    @JsonIgnore
    @Column(name = "idCarrinho")
    private Integer idCarrinho;

    @JsonIgnore
    @Column(name = "idJogo")
    private Integer idJogo;
}
