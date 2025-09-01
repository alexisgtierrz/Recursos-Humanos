package com.rrhh.Recursos_Humanos.Servicios;

import com.rrhh.Recursos_Humanos.Modelos.Usuario;
import com.rrhh.Recursos_Humanos.Repositorios.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testLoadUserByUsername_UserFound() {
        // Arrange
        String username = "testuser";
        Usuario mockUsuario = new Usuario(username, "password", Collections.emptySet());
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.of(mockUsuario));

        // Act
        UserDetails userDetails = usuarioService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        verify(usuarioRepository, times(1)).findByUsername(username);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonexistentuser";
        when(usuarioRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.loadUserByUsername(username);
        });
        verify(usuarioRepository, times(1)).findByUsername(username);
    }
}