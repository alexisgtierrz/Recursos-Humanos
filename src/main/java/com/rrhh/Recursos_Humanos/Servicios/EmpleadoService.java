package com.rrhh.Recursos_Humanos.Servicios;

import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Repositorios.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado guardarEmpleado(Empleado empleado) {
        // Validación de DNI existente
        if (empleadoRepository.existsByDni(empleado.getDni())) {
            throw new IllegalArgumentException("Ya existe un empleado con el DNI: " + empleado.getDni());
        }

        // Validación de email existente
        if (empleadoRepository.existsByEmail(empleado.getEmail())) {
            throw new IllegalArgumentException("Ya existe un empleado con el email: " + empleado.getEmail());
        }

        // Validación de teléfono existente
        if (empleadoRepository.existsByTelefono(empleado.getTelefono())) {
            throw new IllegalArgumentException("Ya existe un empleado con el teléfono: " + empleado.getTelefono());
        }

        return empleadoRepository.save(empleado);
    }

    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    public List<Empleado> obtenerPorPuesto(Long puestoId) {
        return empleadoRepository.findByPuestoId(puestoId);
    }
}
