package com.rrhh.Recursos_Humanos.Controladores;

import com.rrhh.Recursos_Humanos.Modelos.Denuncia;
import com.rrhh.Recursos_Humanos.Servicios.DenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/denuncias")
@CrossOrigin(origins = "*")
public class DenunciaController {

    @Autowired
    private DenunciaService denunciaService;

    // Listar denuncias
    @GetMapping
    public List<Denuncia> listarDenuncias() {
        return denunciaService.obtenerTodas();
    }

    // Crear denuncia y asignarla a un empleado
    @PostMapping("/empleado/{empleadoId}")
    public Denuncia crearDenuncia(@PathVariable Long empleadoId, @RequestBody Denuncia denuncia) {
        return denunciaService.crearDenuncia(empleadoId, denuncia);
    }

    // Obtener denuncias de un empleado
    @GetMapping("/empleado/{empleadoId}")
    public List<Denuncia> obtenerDenunciasEmpleado(@PathVariable Long empleadoId) {
        return denunciaService.obtenerDenunciasPorEmpleado(empleadoId);
    }

    // Eliminar una denuncia
    @DeleteMapping("/{id}")
    public void eliminarDenuncia(@PathVariable Long id) {
        denunciaService.eliminarDenuncia(id);
    }
}
