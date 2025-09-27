package com.rrhh.Recursos_Humanos.TestUnitario;


import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Repositorios.EmpleadoRepository;
import com.rrhh.Recursos_Humanos.Servicios.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmpleadoServiceTest {

    private EmpleadoRepository empleadoRepository;
    private EmpleadoService empleadoService;

    @BeforeEach
    void setUp() {
        empleadoRepository = mock(EmpleadoRepository.class);
        empleadoService = new EmpleadoService(empleadoRepository);
    }

    /*
    @Test
    void listarEmpleados_DeberiaRetornarLista() {
        // Arrange
        Empleado empleado1 = new Empleado("Juan", "Pérez", "juan.perez@example.com", "1234567890", , 50000.0);
        Empleado empleado2 = new Empleado("Ana", "García", "ana.garcia@example.com", "0987654321", , 60000.0);
        when(empleadoRepository.findAll()).thenReturn(List.of(empleado1, empleado2));

        // Act
        List<Empleado> empleados = empleadoService.listarEmpleados();

        // Assert
        assertEquals(2, empleados.size());
        assertEquals("Juan", empleados.get(0).getNombre());
        assertEquals("Ana", empleados.get(1).getNombre());
        verify(empleadoRepository, times(1)).findAll();
    }

    @Test
    void obtenerEmpleadoPorId_DeberiaRetornarEmpleado() {
        // Arrange
        Empleado empleado = new Empleado("Juan", "Pérez", "juan.perez@example.com", "1234567890", "Desarrollador", 50000.0);
        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));

        // Act
        Optional<Empleado> resultado = empleadoService.obtenerEmpleadoPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(empleadoRepository, times(1)).findById(1L);
    }

    @Test
    void guardarEmpleado_DeberiaRetornarEmpleadoGuardado() {
        // Arrange
        Empleado empleado = new Empleado("Ana", "García", "ana.garcia@example.com", "0987654321", "Analista", 60000.0);
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        // Act
        Empleado resultado = empleadoService.guardarEmpleado(empleado);

        // Assert
        assertEquals("Ana", resultado.getNombre());
        verify(empleadoRepository, times(1)).save(empleado);
    }*/

    @Test
    void eliminarEmpleado_DeberiaLlamarDeleteById() {
        // Act
        empleadoService.eliminarEmpleado(1L);

        // Assert
        verify(empleadoRepository, times(1)).deleteById(1L);
    }
}
