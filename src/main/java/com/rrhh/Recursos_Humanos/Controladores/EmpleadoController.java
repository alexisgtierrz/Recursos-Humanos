package com.rrhh.Recursos_Humanos.Controladores;

import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Servicios.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // Listar empleados
    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoService.listarEmpleados();
    }

    // Buscar empleado por id
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Crear empleado
    @PostMapping
    public Empleado guardarEmpleado(@Valid @RequestBody Empleado empleado) {
        return empleadoService.guardarEmpleado(empleado);
    }

    // Editar empleado
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        try {
            // Llama al servicio con AMBOS datos: el ID de la URL y el cuerpo del empleado
            // El servicio se encarga de buscar, validar y guardar.
            Empleado empleadoActualizado = empleadoService.actualizarEmpleado(id, empleado);

            return ResponseEntity.ok(empleadoActualizado);

        } catch (RuntimeException e) {
            if (e.getMessage().contains("Empleado no encontrado")) {
                return ResponseEntity.notFound().build();
            }

            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al actualizar el empleado");
        }
    }

    //Editar empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(e -> {
                    empleadoService.eliminarEmpleado(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    //Buscar empleado por puesto
    @GetMapping("/puesto/{puestoId}")
    public List<Empleado> obtenerEmpleadosPorPuesto(@PathVariable Long puestoId) {
        return empleadoService.obtenerPorPuesto(puestoId);
    }
}
