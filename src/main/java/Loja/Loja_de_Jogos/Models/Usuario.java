package Loja.Loja_de_Jogos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String cpf;

    private String nome;

    private String email;

    private String senha;

    private String telefone;

    private LocalDate datacriacao;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cargo cargo;

    @OneToOne(fetch = FetchType.LAZY)
    private Carrinho carrinho;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(cargo);
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
