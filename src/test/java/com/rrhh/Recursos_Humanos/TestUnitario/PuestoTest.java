package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Puesto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PuestoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void nombreNoPuedeEstarVacio() {
        Puesto puesto = new Puesto();
        puesto.setNombre(""); // inválido
        puesto.setDescripcion("Descripción válida");

        Set<ConstraintViolation<Puesto>> violaciones = validator.validate(puesto);

        boolean tieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre") &&
                        v.getMessage().equals("El nombre del puesto no puede estar vacío"));

        assertTrue(tieneMensaje, "El nombre no puede estar vacío");
    }

    @Test
    void nombreNoPuedeSerNull() {
        Puesto puesto = new Puesto();
        puesto.setNombre(null); // inválido
        puesto.setDescripcion("Descripción válida");

        Set<ConstraintViolation<Puesto>> violaciones = validator.validate(puesto);

        boolean tieneMensaje = violaciones.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nombre") &&
                        v.getMessage().equals("El nombre del puesto no puede estar vacío"));

        assertTrue(tieneMensaje, "El nombre no puede ser null");
    }

    @Test
    void descripcionPuedeEstarVaciaONull() {
        Puesto puesto1 = new Puesto();
        puesto1.setNombre("Desarrollador");
        puesto1.setDescripcion("");

        Puesto puesto2 = new Puesto();
        puesto2.setNombre("Tester");
        puesto2.setDescripcion(null);

        Set<ConstraintViolation<Puesto>> violaciones1 = validator.validate(puesto1);
        Set<ConstraintViolation<Puesto>> violaciones2 = validator.validate(puesto2);

        assertTrue(violaciones1.isEmpty(), "La descripción vacía es válida");
        assertTrue(violaciones2.isEmpty(), "La descripción null es válida");
    }
}

