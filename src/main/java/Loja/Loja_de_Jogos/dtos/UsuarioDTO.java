package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.UserRole;
import Loja.Loja_de_Jogos.Models.Usuario;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone,
        String role
) {
    public static UsuarioDTO fromEntity(Usuario u) {
        return new UsuarioDTO(
                u.getId(),
                u.getNome(),
                u.getEmail(),
                u.getCpf(),
                u.getTelefone(),
                u.getRole().name()
        );
    }
}
