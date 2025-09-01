package com.rrhh.Recursos_Humanos.Controladores;

import com.rrhh.Recursos_Humanos.Servicios.UsuarioService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") 
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioService usuarioService; // Ahora es una variable 'final'

    // El constructor debe inyectar TODAS las dependencias
    public AuthController(AuthenticationManager authManager, UsuarioService usuarioService) {
        this.authManager = authManager;
        this.usuarioService = usuarioService; 
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login exitoso");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }
    }

    @Data
    public static class LoginRequest {
        private String usuario;
        private String password;
    }
}