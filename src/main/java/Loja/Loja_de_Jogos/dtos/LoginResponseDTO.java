package Loja.Loja_de_Jogos.dtos;

public record LoginResponseDTO(
        String token,
        UsuarioDTO user
) {}
