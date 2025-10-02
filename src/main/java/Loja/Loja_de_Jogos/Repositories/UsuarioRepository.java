package Loja.Loja_de_Jogos.Repositories;

import Loja.Loja_de_Jogos.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
