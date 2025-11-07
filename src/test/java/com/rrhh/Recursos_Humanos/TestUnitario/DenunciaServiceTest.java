package com.rrhh.Recursos_Humanos.TestUnitario;

import com.rrhh.Recursos_Humanos.Modelos.Denuncia;
import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Repositorios.DenunciaRepository;
import com.rrhh.Recursos_Humanos.Repositorios.EmpleadoRepository;
import com.rrhh.Recursos_Humanos.Servicios.DenunciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DenunciaServiceTest {

    private DenunciaRepository denunciaRepository;
    private EmpleadoRepository empleadoRepository;
    private DenunciaService denunciaService;

    @BeforeEach
    void setup() {
        denunciaRepository = mock(DenunciaRepository.class);
        empleadoRepository = mock(EmpleadoRepository.class);
        denunciaService = new DenunciaService();
        denunciaService.denunciaRepository = denunciaRepository;
        denunciaService.empleadoRepositorio = empleadoRepository;
    }

    @Test
    void crearDenuncia_DeberiaGuardarDenunciaSiEmpleadoExiste() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);

        Denuncia denuncia = new Denuncia("Llegó tarde");

        when(empleadoRepository.findById(1L)).thenReturn(Optional.of(empleado));
        when(denunciaRepository.save(denuncia)).thenReturn(denuncia);

        Denuncia resultado = denunciaService.crearDenuncia(1L, denuncia);

        assertEquals("Llegó tarde", resultado.getDescripcion());
        verify(empleadoRepository, times(1)).findById(1L);
        verify(denunciaRepository, times(1)).save(denuncia);
    }

    @Test
    void crearDenuncia_DeberiaLanzarExcepcionSiEmpleadoNoExiste() {
        Denuncia denuncia = new Denuncia("Llegó tarde");

        when(empleadoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class,
                () -> denunciaService.crearDenuncia(1L, denuncia));

        assertEquals("Empleado no encontrado con ID: 1", excepcion.getMessage());
        verify(empleadoRepository, times(1)).findById(1L);
        verify(denunciaRepository, never()).save(any());
    }

    @Test
    void obtenerDenunciasPorEmpleado_DeberiaRetornarLista() {
        Denuncia denuncia1 = new Denuncia("Falta de respeto");
        Denuncia denuncia2 = new Denuncia("Uso indebido del celular");

        when(denunciaRepository.findByEmpleadoId(1L)).thenReturn(List.of(denuncia1, denuncia2));

        List<Denuncia> resultado = denunciaService.obtenerDenunciasPorEmpleado(1L);

        assertEquals(2, resultado.size());
        verify(denunciaRepository, times(1)).findByEmpleadoId(1L);
    }

    @Test
    void eliminarDenuncia_DeberiaLlamarDeleteById() {
        denunciaService.eliminarDenuncia(5L);

        verify(denunciaRepository, times(1)).deleteById(5L);
    }

    @Test
    void obtenerTodas_DeberiaRetornarTodasLasDenuncias() {
        Denuncia denuncia1 = new Denuncia("Falta de respeto");
        Denuncia denuncia2 = new Denuncia("Uso indebido del celular");

        when(denunciaRepository.findAll()).thenReturn(List.of(denuncia1, denuncia2));

        List<Denuncia> resultado = denunciaService.obtenerTodas();

        assertEquals(2, resultado.size());
        verify(denunciaRepository, times(1)).findAll();
    }

    @Test
    void guardar_DeberiaGuardarDenuncia() {
        Denuncia denuncia = new Denuncia("Incumplimiento de normas");

        when(denunciaRepository.save(denuncia)).thenReturn(denuncia);

        Denuncia resultado = denunciaService.guardar(denuncia);

        assertEquals("Incumplimiento de normas", resultado.getDescripcion());
        verify(denunciaRepository, times(1)).save(denuncia);
    }
}

