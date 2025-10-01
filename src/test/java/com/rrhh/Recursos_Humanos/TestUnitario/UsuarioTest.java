package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Usuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    private final Validator validator;

    public UsuarioTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void usuarioValido_NoDebeTenerViolaciones() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan123");
        usuario.setPassword("Password1");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validate(usuario);
        assertTrue(violaciones.isEmpty(), "No debería haber violaciones cuando todos los atributos son válidos");
    }

    @Test
    void usernameNoPuedeSerNull() {
        Usuario usuario = new Usuario();
        usuario.setUsername(null);
        usuario.setPassword("Password1");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "username");
        assertFalse(violaciones.isEmpty(), "El username no puede ser null");
    }

    @Test
    void usernameConMenosDe3Caracteres() {
        Usuario usuario = new Usuario();
        usuario.setUsername("al");
        usuario.setPassword("Password1");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "username");
        assertFalse(violaciones.isEmpty(), "El username no puede tener menos de 3 caracteres");
    }

    @Test
    void usernameConMassDe20Caracteres() {
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario_demasiado_largo123");
        usuario.setPassword("Password1");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "username");
        assertFalse(violaciones.isEmpty(), "El username no puede tener mas de 20 caracteres");
    }

    @Test
    void passwordNoPuedeSerNull() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan123");
        usuario.setPassword(null);
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "password");
        assertFalse(violaciones.isEmpty(), "El password no puede ser null");
    }

    @Test
    void passwordConMenosDe8Caracteres() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan123");
        usuario.setPassword("al");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "password");
        assertFalse(violaciones.isEmpty(), "El password no puede tener menos de 3 caracteres");
    }

    @Test
    void passwordSinMinuscula() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan123");
        usuario.setPassword("PASSWORD123");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "password");
        assertFalse(violaciones.isEmpty(), "El password debe tener al menos una minuscula");
    }

    @Test
    void passwordSinMayuscula() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan123");
        usuario.setPassword("password123");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "password");
        assertFalse(violaciones.isEmpty(), "El password debe tener al menos una mayuscula");
    }

    @Test
    void passwordSinNumero() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan123");
        usuario.setPassword("Password");
        usuario.setRoles(Set.of("USER"));

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "password");
        assertFalse(violaciones.isEmpty(), "El password debe tener al menos un numero");
    }

    @Test
    void rolesNoPuedeSerNull() {
        Usuario usuario = new Usuario();
        usuario.setUsername("juan123");
        usuario.setPassword("Password1");
        usuario.setRoles(null);

        Set<ConstraintViolation<Usuario>> violaciones = validator.validateProperty(usuario, "roles");
        assertFalse(violaciones.isEmpty(), "El conjunto de roles no puede ser null");
    }
}
