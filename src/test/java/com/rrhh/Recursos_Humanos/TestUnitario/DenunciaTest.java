package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Denuncia;
import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DenunciaTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void descripcionNoDebeEstarVacia() {
        Denuncia denuncia = new Denuncia();
        denuncia.setDescripcion(""); // inválido
        denuncia.setEmpleado(new Empleado()); // se debe asignar un empleado para no fallar por nullable

        Set<ConstraintViolation<Denuncia>> violaciones = validator.validate(denuncia);

        assertFalse(violaciones.isEmpty(), "La descripción vacía debería ser inválida");
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción de la denuncia no puede estar vacía"));
        assertTrue(contieneMensaje);
    }

    @Test
    void descripcionValida_NoDebeTenerViolaciones() {
        Denuncia denuncia = new Denuncia();
        denuncia.setDescripcion("Uso indebido del celular");
        denuncia.setEmpleado(new Empleado()); // se debe asignar un empleado para no fallar por nullable

        Set<ConstraintViolation<Denuncia>> violaciones = validator.validate(denuncia);

        assertTrue(violaciones.isEmpty(), "No debería haber violaciones cuando la descripción es válida");
    }

    @Test
    void empleadoNoPuedeSerNull() {
        Denuncia denuncia = new Denuncia();
        denuncia.setDescripcion("Llegó tarde"); // válido
        denuncia.setEmpleado(null); // inválido

        Set<ConstraintViolation<Denuncia>> violaciones = validator.validate(denuncia);

        assertFalse(violaciones.isEmpty(), "El empleado no puede ser null");
        boolean contieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getMessage().contains("El empleado no puede ser null")); // JPA @ManyToOne genera este mensaje
        assertTrue(contieneMensaje);
    }
}
