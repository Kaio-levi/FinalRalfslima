package Loja.Loja_de_Jogos.Repositories;

import Loja.Loja_de_Jogos.Models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
