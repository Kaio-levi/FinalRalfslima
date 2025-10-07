package Loja.Loja_de_Jogos.Models;

public enum Status {
    APROVADO(1),
    RECUSADO(2),
    PENDENTE(3);

    private final Integer codigo;

    Status(Integer codigo){
        this.codigo = codigo;
    }
}
