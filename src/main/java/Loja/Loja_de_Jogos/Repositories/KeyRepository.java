package Loja.Loja_de_Jogos.Repositories;

import Loja.Loja_de_Jogos.Models.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
}
