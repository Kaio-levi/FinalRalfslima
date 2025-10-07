package Loja.Loja_de_Jogos.DTO;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String email;
    private String password;
}