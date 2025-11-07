package com.rrhh.Recursos_Humanos.Servicios;

import com.rrhh.Recursos_Humanos.Modelos.Puesto;
import com.rrhh.Recursos_Humanos.Repositorios.PuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuestoService {

    private final PuestoRepository puestoRepository;

    public PuestoService(PuestoRepository puestoRepository) {
        this.puestoRepository = puestoRepository;
    }

    public List<Puesto> listarPuestos() {
        return puestoRepository.findAll();
    }

    public Optional<Puesto> obtenerPuestoPorId(Long id) {
        return puestoRepository.findById(id);
    }

    public Puesto guardarPuesto(Puesto puesto) {
        return puestoRepository.save(puesto);
    }

    public void eliminarPuesto(Long id) {
        puestoRepository.deleteById(id);
    }
}
