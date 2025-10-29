package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Usuario;
import com.rrhh.Recursos_Humanos.Repositorios.UsuarioRepository;
import com.rrhh.Recursos_Humanos.Servicios.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void loadUserByUsername_UsuarioExistente_DeberiaRetornarUserDetails() {
        Usuario usuario = new Usuario("usuario123", "pass123", Set.of("ADMIN", "USER"));
        when(usuarioRepository.findByUsername("usuario123")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = usuarioService.loadUserByUsername("usuario123");

        assertEquals("usuario123", userDetails.getUsername());
        assertEquals("pass123", userDetails.getPassword());

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        verify(usuarioRepository, times(1)).findByUsername("usuario123");
    }

    @Test
    void loadUserByUsername_UsuarioNoExistente_DeberiaLanzarExcepcion() {
        when(usuarioRepository.findByUsername("inexistente")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> usuarioService.loadUserByUsername("inexistente"));

        assertEquals("Usuario no encontrado: inexistente", exception.getMessage());
        verify(usuarioRepository, times(1)).findByUsername("inexistente");
    }

    @Test
    void validarCredenciales_Correctas_DeberiaRetornarTrue() {
        Usuario usuario = new Usuario("usuario123", "pass123", Set.of("ADMIN"));
        when(usuarioRepository.findByUsername("usuario123")).thenReturn(Optional.of(usuario));

        boolean resultado = usuarioService.validarCredenciales("usuario123", "pass123");

        assertTrue(resultado);
        verify(usuarioRepository, times(1)).findByUsername("usuario123");
    }

    @Test
    void validarCredenciales_Incorrectas_DeberiaRetornarFalse() {
        Usuario usuario = new Usuario("usuario123", "pass123", Set.of("ADMIN"));
        when(usuarioRepository.findByUsername("usuario123")).thenReturn(Optional.of(usuario));

        boolean resultado = usuarioService.validarCredenciales("usuario123", "wrongpass");

        assertFalse(resultado);
        verify(usuarioRepository, times(1)).findByUsername("usuario123");
    }

    @Test
    void validarCredenciales_UsuarioNoExistente_DeberiaRetornarFalse() {
        when(usuarioRepository.findByUsername("inexistente")).thenReturn(Optional.empty());

        boolean resultado = usuarioService.validarCredenciales("inexistente", "pass123");

        assertFalse(resultado);
        verify(usuarioRepository, times(1)).findByUsername("inexistente");
    }
}
