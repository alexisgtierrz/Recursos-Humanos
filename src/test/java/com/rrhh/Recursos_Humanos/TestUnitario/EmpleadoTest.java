package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Modelos.Puesto;
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
    void empleadoValido_NoDebeTenerViolaciones() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890"); // EXACTAMENTE 10 dígitos
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador"); // No nulo y válido
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        // Act
        Set<ConstraintViolation<Empleado>> violaciones = validator.validate(empleado);

        // Assert
        assertTrue(violaciones.isEmpty(), "No debería haber violaciones cuando todos los atributos son válidos");
    }


    @Test
    void nombreNoPuedeContenerNumeros() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan123");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "nombre");
        assertFalse(violaciones.isEmpty(), "El nombre con números debería ser inválido");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void nombreNoPuedeContenerEspacios() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Jua n");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "nombre");
        assertFalse(violaciones.isEmpty(), "El nombre con espacios debería ser inválido");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void nombreNoPuedeContenerCaracteresEspeciales() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Jua_n");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "nombre");
        assertFalse(violaciones.isEmpty(), "El nombre con caracteres especiales debería ser inválido");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void apellidoNoPuedeContenerNumeros() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez123");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "apellido");
        assertFalse(violaciones.isEmpty(), "El apellido con números debería ser inválido");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El apellido solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void apellidoNoPuedeContenerEspacios() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pé rez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "apellido");
        assertFalse(violaciones.isEmpty(), "El apellido con espacios debería ser inválido");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El apellido solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void apellidoNoPuedeContenerCaracteresEspeciales() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pé_rez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "apellido");
        assertFalse(violaciones.isEmpty(), "El apellido con caracteres especiales debería ser inválido");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El apellido solo puede contener letras sin espacios ni caracteres especiales"));
        assertTrue(contieneMensaje);
    }

    @Test
    void emailInvalido() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perezexample.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "email");
        assertFalse(violaciones.isEmpty(), "El email inválido debería producir una violación");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El email debe tener un formato válido (ejemplo@dominio.com)"));
        assertTrue(contieneMensaje);
    }

    @Test
    void telefonoNoPuedeContenerLetras() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("a234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(50000.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "telefono");
        assertFalse(violaciones.isEmpty(), "El teléfono con letras debería ser inválido");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El teléfono debe contener exactamente 10 dígitos numéricos"));
        assertTrue(contieneMensaje);
    }


    @Test
    void salarioNoPuedeSerNull() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(null);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "salario");
        assertFalse(violaciones.isEmpty(), "El salario no puede ser null");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El salario no puede estar vacío"));
        assertTrue(contieneMensaje);
    }

    @Test
    void salarioDebeSerMayorQueCero() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Pérez");
        empleado.setDni("44077666");
        empleado.setFechaNacimiento("01/01/2001");
        empleado.setEmail("juan.perez@example.com");
        empleado.setTelefono("1234567890");
        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        empleado.setPuesto(puesto);
        empleado.setSalario(0.0);

        Set<ConstraintViolation<Empleado>> violaciones = validator.validateProperty(empleado, "salario");
        assertFalse(violaciones.isEmpty(), "El salario debe ser mayor que 0");

        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("El salario debe ser mayor que 0"));
        assertTrue(contieneMensaje);
    }

}
