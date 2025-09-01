package com.rrhh.Recursos_Humanos.Controladores;

import com.rrhh.Recursos_Humanos.Servicios.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authManager;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testLogin_Success() throws Exception {
        // Arrange
        String loginRequestJson = "{\"usuario\":\"testuser\", \"password\":\"password\"}";
        when(authManager.authenticate(any())).thenReturn(null); // Return a non-null object for success

        // Act and Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk());
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        // Arrange
        String loginRequestJson = "{\"usuario\":\"wronguser\", \"password\":\"wrongpass\"}";
        when(authManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act and Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isUnauthorized());
    }
}