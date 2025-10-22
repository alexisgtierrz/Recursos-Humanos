package com.rrhh.Recursos_Humanos.TestIntegracion;

import com.rrhh.Recursos_Humanos.Controladores.AuthController;
import com.rrhh.Recursos_Humanos.Modelos.Usuario;
import com.rrhh.Recursos_Humanos.Servicios.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testLoginExitoso() throws Exception {
        Usuario usuario = new Usuario(1L, "admin", "1234");
        when(usuarioService.autenticar("admin", "1234")).thenReturn(usuario);

        String json = """
            {"usuario":"admin","contrasena":"1234"}
        """;

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario").value("admin"));
    }
}
