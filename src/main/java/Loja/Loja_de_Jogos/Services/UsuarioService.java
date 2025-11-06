package Loja.Loja_de_Jogos.Services;

import Loja.Loja_de_Jogos.Models.Usuario;
import Loja.Loja_de_Jogos.Repositories.UsuarioRepository;
import Loja.Loja_de_Jogos.dtos.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    public boolean emailExiste(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }


    private UsuarioRepository usuarioRepository;
    private ObjectMapper objectMapper;

    public UsuarioService(ObjectMapper objectMapper, UsuarioRepository usuarioRepository) {
        this.objectMapper = objectMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> listarTodos() {
        List<UsuarioDTO> lista = usuarioRepository.findAll()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
        return lista;
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean deletar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long count() {
        return usuarioRepository.count();
    }
}