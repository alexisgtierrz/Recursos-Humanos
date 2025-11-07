package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Controladores.AuthController;
import com.rrhh.Recursos_Humanos.Controladores.AuthController.LoginRequest;
import com.rrhh.Recursos_Humanos.Servicios.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {
    private AuthenticationManager authManager;
    private UsuarioService usuarioService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authManager = mock(AuthenticationManager.class);
        usuarioService = mock(UsuarioService.class);
        authController = new AuthController(authManager);
        authController.usuarioService = usuarioService;
    }

    @Test
    void login_CredencialesCorrectas_DeberiaRetornarExito() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsuario("juan");
        request.setPassword("1234");

        Authentication authMock = mock(Authentication.class);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);

        ResponseEntity<?> response = authController.login(request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login exitoso", response.getBody());
        assertEquals(authMock, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void login_CredencialesIncorrectas_DeberiaRetornar401() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsuario("juan");
        request.setPassword("wrongpass");

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(""));

        ResponseEntity<?> response = authController.login(request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Usuario o contraseña incorrectos", response.getBody());
    }
}
