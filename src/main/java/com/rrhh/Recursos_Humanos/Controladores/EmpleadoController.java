package com.rrhh.Recursos_Humanos.Controladores;

import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Servicios.EmpleadoService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoService.listarEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Empleado guardarEmpleado(@Valid @RequestBody Empleado empleado) {
        return empleadoService.guardarEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado empleado) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(e -> {
                    e.setNombre(empleado.getNombre());
                    e.setApellido(empleado.getApellido());
                    e.setDni(empleado.getDni());
                    e.setFechaNacimiento(empleado.getFechaNacimiento());
                    e.setEmail(empleado.getEmail());
                    e.setTelefono(empleado.getTelefono());
                    e.setPuesto(empleado.getPuesto());
                    e.setSalario(empleado.getSalario());
                    return ResponseEntity.ok(empleadoService.guardarEmpleado(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Long id) {
        return empleadoService.obtenerEmpleadoPorId(id)
                .map(e -> {
                    empleadoService.eliminarEmpleado(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
