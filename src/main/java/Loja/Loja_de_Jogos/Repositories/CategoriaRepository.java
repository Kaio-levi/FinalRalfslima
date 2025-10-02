package Loja.Loja_de_Jogos.Repositories;

import Loja.Loja_de_Jogos.Models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
