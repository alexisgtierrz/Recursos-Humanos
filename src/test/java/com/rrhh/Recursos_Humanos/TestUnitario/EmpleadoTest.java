package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    private final Validator validator;

    public EmpleadoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorVacio() {
        Empleado empleado = new Empleado();
        assertNotNull(empleado);
    }


    @Test
    void testGettersYSetters() {
        Empleado empleado = new Empleado();

        empleado.setId(1L);
        empleado.setNombre("Ana");
        empleado.setApellido("García");
        empleado.setEmail("ana.garcia@example.com");
        empleado.setTelefono("987654321");
        empleado.setSalario(60000.0);

        assertEquals(1L, empleado.getId());
        assertEquals("Ana", empleado.getNombre());
        assertEquals("García", empleado.getApellido());
        assertEquals("ana.garcia@example.com", empleado.getEmail());
        assertEquals("987654321", empleado.getTelefono());
        assertEquals("Analista", empleado.getPuesto());
        assertEquals(60000.0, empleado.getSalario());
    }

    @Test
    void empleadoValido_NoDebeTenerViolaciones() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890"); // 10 dígitos
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validate(empleado);

        // Assert: no debe haber violaciones
        assertTrue(violaciones.isEmpty(), "No debería haber violaciones cuando todos los atributos son válidos");
    }

    @Test
    void nombreNoPuedeContenerNumeros() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan123"); // Nombre inválido con números
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "nombre");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El nombre con números debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void nombreNoPuedeContenerEspacios() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Jua n"); // Nombre inválido con números
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "nombre");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El nombre con espacios debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void nombreNoPuedeContenerCaracteresEspeciales() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Jua_n"); // Nombre inválido con números
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "nombre");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El nombre con caracteres especiales debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }



    @Test
    void apellidoNoPuedeContenerNumeros() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan"); // Nombre inválido con números
        empleado.setApellido("Pérez123");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "apellido");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El apellido con números debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El apellido solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void apellidoNoPuedeContenerEspacios() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Jua n"); // Nombre inválido con números
        empleado.setApellido("Pé rez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "apellido");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El apellido con espacios debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El apellido solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void apellidoNoPuedeContenerCaracteresEspeciales() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan"); // Nombre inválido con números
        empleado.setApellido("Pé_rez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "apellido");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El apellido con caracteres especiales debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El apellido solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void emailInvalido() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan"); // Nombre inválido con números
        empleado.setApellido("Pérez123");
        empleado.setEmail("juan.perezexample.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "email");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El email con números debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El email debe tener un formato válido (ejemplo@dominio.com)"));
        assertTrue(contieneMensaje);
    }

    @Test
    void telefonoNoPuedeContenerLetras() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan"); // Nombre inválido con números
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("a234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "telefono");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El telefono con letras debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El teléfono debe contener exactamente 10 dígitos numéricos"));
        assertTrue(contieneMensaje);
    }

    @Test
    void puestoNoPuedeContenerNumeros() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan"); // Nombre inválido con números
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "puesto");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El puesto con números debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El puesto solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void puestoNoPuedeContenerEspacios() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan"); // Nombre inválido con números
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "puesto");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El puesto con espacios debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El puesto solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void puestoNoPuedeContenerCaracteresEspeciales() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan"); // Nombre inválido con números
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "puesto");

        // Assert: debe haber al menos una violación porque el nombre tiene números
        assertFalse(violaciones.isEmpty(), "El puesto con caracteres especiales debería ser inválido");
        //verificar el mensaje exacto
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El puesto solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void salarioNoPuedeSerNull() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(null); // Salario inválido

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "salario");

        // Assert
        assertFalse(violaciones.isEmpty(), "El salario no puede ser null");
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El salario no puede estar vacío"));
        assertTrue(contieneMensaje);
    }

    @Test
    void salarioDebeSerMayorQueCero() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(0.0); // Salario inválido

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "salario");

        // Assert
        assertFalse(violaciones.isEmpty(), "El salario debe ser mayor que 0");
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El salario debe ser mayor que 0"));
        assertTrue(contieneMensaje);
    }

    @Test
    void salarioNoPuedeTenerMasDe2Decimales() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        empleado.setSalario(1000.123); // Más de 2 decimales

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "salario");

        // Assert
        assertFalse(violaciones.isEmpty(), "El salario no puede tener más de 2 decimales");
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El salario debe tener hasta 10 dígitos enteros y 2 decimales"));
        assertTrue(contieneMensaje);
    }


}
