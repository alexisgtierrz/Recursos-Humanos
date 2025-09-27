package com.rrhh.Recursos_Humanos.Controladores;


import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Modelos.Puesto;
import com.rrhh.Recursos_Humanos.Repositorios.PuestoRepository;
import com.rrhh.Recursos_Humanos.Servicios.PuestoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puestos")
public class PuestoController {

    private final PuestoRepository puestoRepository;
    private final PuestoService puestoService;

    public PuestoController(PuestoRepository puestoRepository, PuestoService puestoService) {
        this.puestoRepository = puestoRepository;
        this.puestoService = puestoService;
    }

    @GetMapping
    public List<Puesto> listarPuestos() {
        return puestoRepository.findAll();
    }

    @PostMapping
    public Puesto guardarPuesto(@Valid @RequestBody Puesto puesto) {
        return puestoService.guardarPuesto(puesto);
    }
}

