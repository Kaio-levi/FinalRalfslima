package Loja.Loja_de_Jogos.Models;

public enum Plataforma {
    PC(1),
    PS5(2),
    PS4(3),
    XBOX(4),
    NINTENDO_SWITCH(5),
    MOBILE(6);

    private final Integer codigo;

    Plataforma(Integer codigo){
        this.codigo = codigo;
    }
}
