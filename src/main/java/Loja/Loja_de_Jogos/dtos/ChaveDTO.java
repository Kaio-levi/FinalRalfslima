package Loja.Loja_de_Jogos.dtos;

import Loja.Loja_de_Jogos.Models.Chave;
import Loja.Loja_de_Jogos.Models.Status;

public class ChaveDTO {
    public Long id;
    public String codigo;
    public String status;

    public ChaveDTO(Chave chave) {
        this.id = chave.getId();
        this.codigo = chave.getCodigo();
        this.status = chave.getStatus() != null ? chave.getStatus().name() : null;
    }
}
