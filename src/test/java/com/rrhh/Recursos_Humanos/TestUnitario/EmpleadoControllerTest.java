package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Controladores.EmpleadoController;
import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Servicios.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmpleadoControllerTest {

    private EmpleadoService empleadoService;
    private EmpleadoController empleadoController;

    @BeforeEach
    void setUp() {
        empleadoService = mock(EmpleadoService.class);
        empleadoController = new EmpleadoController(empleadoService);
    }

    @Test
    void listarEmpleados_DeberiaRetornarLista() {
        Empleado e1 = new Empleado();
        Empleado e2 = new Empleado();
        when(empleadoService.listarEmpleados()).thenReturn(Arrays.asList(e1, e2));

        List<Empleado> result = empleadoController.listarEmpleados();

        assertEquals(2, result.size());
        verify(empleadoService, times(1)).listarEmpleados();
    }

    @Test
    void obtenerEmpleadoPorId_Existente_DeberiaRetornarEmpleado() {
        Empleado e = new Empleado();
        e.setId(1L);
        when(empleadoService.obtenerEmpleadoPorId(1L)).thenReturn(Optional.of(e));

        ResponseEntity<Empleado> response = empleadoController.obtenerEmpleadoPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(e, response.getBody());
    }

    @Test
    void obtenerEmpleadoPorId_NoExistente_DeberiaRetornar404() {
        when(empleadoService.obtenerEmpleadoPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Empleado> response = empleadoController.obtenerEmpleadoPorId(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void guardarEmpleado_DeberiaLlamarAlService() {
        Empleado e = new Empleado();
        when(empleadoService.guardarEmpleado(e)).thenReturn(e);

        Empleado result = empleadoController.guardarEmpleado(e);

        assertEquals(e, result);
        verify(empleadoService, times(1)).guardarEmpleado(e);
    }

    @Test
    void actualizarEmpleado_Existente_DeberiaActualizar() {
        Empleado existente = new Empleado();
        existente.setId(1L);

        Empleado actualizado = new Empleado();
        actualizado.setNombre("NuevoNombre");

        when(empleadoService.actualizarEmpleado(1L, existente))
                .thenReturn(actualizado);

        ResponseEntity<?> response = empleadoController.actualizarEmpleado(1L, existente);
        Empleado responseBody = (Empleado) response.getBody();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("NuevoNombre", responseBody.getNombre());
    }

    @Test
    void actualizarEmpleado_NoExistente_DeberiaRetornar404() {
        // Datos del empleado que enviaremos en el body
        Empleado empleadoActualizadoBody = new Empleado();
        empleadoActualizadoBody.setNombre("NoImporta");

        when(empleadoService.actualizarEmpleado(1L, empleadoActualizadoBody))
                .thenThrow(new RuntimeException("Empleado no encontrado con id: 1"));

        // Llamar al controlador
        ResponseEntity<?> response = empleadoController.actualizarEmpleado(1L, empleadoActualizadoBody);

        // Verificar que el controlador manejó la excepción y la convirtió en 404
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void eliminarEmpleado_Existente_DeberiaLlamarService() {
        Empleado e = new Empleado();
        when(empleadoService.obtenerEmpleadoPorId(1L)).thenReturn(Optional.of(e));

        ResponseEntity<Void> response = empleadoController.eliminarEmpleado(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(empleadoService, times(1)).eliminarEmpleado(1L);
    }

    @Test
    void eliminarEmpleado_NoExistente_DeberiaRetornar404() {
        when(empleadoService.obtenerEmpleadoPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = empleadoController.eliminarEmpleado(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(empleadoService, never()).eliminarEmpleado(1L);
    }
}
