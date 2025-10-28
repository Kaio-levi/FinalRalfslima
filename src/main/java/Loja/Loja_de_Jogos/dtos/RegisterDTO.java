package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RegisterDTO(
        String cpf,
        String nome,
        String email,
        @JsonProperty("senha") String senha,
        String telefone,
        UserRole role
) {}