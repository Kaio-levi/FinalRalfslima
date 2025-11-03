package Loja.Loja_de_Jogos.Repositories;

import Loja.Loja_de_Jogos.Models.CarrinhoJogo;
import Loja.Loja_de_Jogos.Models.CarrinhoJogoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoJogoRepository extends JpaRepository<CarrinhoJogo, CarrinhoJogoId> {
}
