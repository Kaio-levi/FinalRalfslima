package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.Models.Usuario;
import Loja.Loja_de_Jogos.Services.UsuarioService;
import Loja.Loja_de_Jogos.dtos.AuthenticationDTO;
import Loja.Loja_de_Jogos.dtos.LoginResponseDTO;
import Loja.Loja_de_Jogos.dtos.RegisterDTO;
import Loja.Loja_de_Jogos.dtos.UsuarioDTO;
import Loja.Loja_de_Jogos.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final UsuarioService usuarioService;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UsuarioService usuarioService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO dto) {
        System.out.println(dto.email() + " - " + dto.password());
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        Usuario usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.generateToken(usuario);
        UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token, usuarioDTO));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO dto) {
        if (usuarioService.emailExiste(dto.email()))
            return ResponseEntity.badRequest().build();

        String cryptedPassword = new BCryptPasswordEncoder().encode(dto.senha());
        Usuario newUser = new Usuario(dto.cpf(), dto.nome(), dto.email(), cryptedPassword, dto.telefone(), dto.role());

        usuarioService.salvar(newUser);

        UsuarioDTO usuarioDTO = UsuarioDTO.fromEntity(newUser);
        return ResponseEntity.ok(usuarioDTO);
    }
}