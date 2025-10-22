package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Puesto;
import com.rrhh.Recursos_Humanos.Repositorios.PuestoRepository;
import com.rrhh.Recursos_Humanos.Servicios.PuestoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PuestoServiceTest {

    private PuestoRepository puestoRepository;
    private PuestoService puestoService;

    @BeforeEach
    void setUp() {
        puestoRepository = mock(PuestoRepository.class);
        puestoService = new PuestoService(puestoRepository);
    }

    @Test
    void listarPuestos_DeberiaRetornarTodosLosPuestos() {
        Puesto p1 = new Puesto();
        p1.setNombre("Desarrollador");
        Puesto p2 = new Puesto();
        p2.setNombre("Analista");

        when(puestoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Puesto> puestos = puestoService.listarPuestos();

        assertEquals(2, puestos.size());
        assertTrue(puestos.stream().anyMatch(p -> p.getNombre().equals("Desarrollador")));
        assertTrue(puestos.stream().anyMatch(p -> p.getNombre().equals("Analista")));
        verify(puestoRepository, times(1)).findAll();
    }

    @Test
    void obtenerPuestoPorId_CuandoExiste_DeberiaRetornarPuesto() {
        Puesto p = new Puesto();
        p.setId(1L);
        p.setNombre("Tester");

        when(puestoRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Puesto> resultado = puestoService.obtenerPuestoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Tester", resultado.get().getNombre());
        verify(puestoRepository, times(1)).findById(1L);
    }

    @Test
    void guardarPuesto_DeberiaGuardarYRetornarPuesto() {
        Puesto p = new Puesto();
        p.setNombre("DevOps");

        when(puestoRepository.save(p)).thenReturn(p);

        Puesto guardado = puestoService.guardarPuesto(p);

        assertEquals("DevOps", guardado.getNombre());
        verify(puestoRepository, times(1)).save(p);
    }

    @Test
    void eliminarPuesto_DeberiaLlamarDeleteById() {
        puestoService.eliminarPuesto(5L);

        verify(puestoRepository, times(1)).deleteById(5L);
    }
}
