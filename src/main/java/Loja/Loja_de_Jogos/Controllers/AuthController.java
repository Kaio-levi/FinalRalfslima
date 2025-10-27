package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.DTO.AuthenticationDTO;
import Loja.Loja_de_Jogos.DTO.LoginResponseDTO;
import Loja.Loja_de_Jogos.DTO.RegisterDTO;
import Loja.Loja_de_Jogos.Models.Usuario;
import Loja.Loja_de_Jogos.Repositories.UsuarioRepository;
import Loja.Loja_de_Jogos.Security.security.TokenService;
import org.springframework.http.ResponseEntity; // Importar ResponseEntity
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository userRepository;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO dto){
        if(this.userRepository.findByEmail(dto.email()) != null)
            return ResponseEntity.badRequest().build();

        String cryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Usuario newUser = new Usuario(dto.cpf(),dto.nome(),dto.email(), cryptedPassword,dto.telefone(), dto.role());

        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}