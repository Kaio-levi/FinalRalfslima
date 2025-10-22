package Loja.Loja_de_Jogos.Controllers;

import Loja.Loja_de_Jogos.DTO.AuthRequest;
import Loja.Loja_de_Jogos.DTO.AuthResponse;
import Loja.Loja_de_Jogos.Security.JwtUtil;
import org.springframework.http.HttpStatus; // Importar HttpStatus
import org.springframework.http.ResponseEntity; // Importar ResponseEntity
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException; // Importar AuthenticationException
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(authentication.getName());

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (AuthenticationException e) {
            System.err.println("Falha na Autenticação para usuário [" + authRequest.getEmail() + "]: " + e.getMessage());

            AuthResponse errorResponse = new AuthResponse("Credenciais inválidas. Verifique seu e-mail e senha.");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse);
        }
    }
}