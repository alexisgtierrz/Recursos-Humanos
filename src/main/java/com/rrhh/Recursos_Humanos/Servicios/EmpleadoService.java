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

    public Empleado actualizarEmpleado(Long id, Empleado empleadoActualizado) {

        Empleado empleadoAActualizar = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        Optional<Empleado> empleadoConMismoDni = empleadoRepository.findByDni(empleadoActualizado.getDni());
        if (empleadoConMismoDni.isPresent() && !empleadoConMismoDni.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe OTRO empleado con el DNI: " + empleadoActualizado.getDni());
        }

        Optional<Empleado> empleadoConMismoEmail = empleadoRepository.findByEmail(empleadoActualizado.getEmail());
        if (empleadoConMismoEmail.isPresent() && !empleadoConMismoEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe OTRO empleado con el email: " + empleadoActualizado.getEmail());
        }

        Optional<Empleado> empleadoConMismoTelefono = empleadoRepository.findByTelefono(empleadoActualizado.getTelefono());
        if (empleadoConMismoTelefono.isPresent() && !empleadoConMismoTelefono.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ya existe OTRO empleado con el teléfono: " + empleadoActualizado.getTelefono());
        }

        empleadoAActualizar.setNombre(empleadoActualizado.getNombre());
        empleadoAActualizar.setApellido(empleadoActualizado.getApellido());
        empleadoAActualizar.setDni(empleadoActualizado.getDni());
        empleadoAActualizar.setFechaNacimiento(empleadoActualizado.getFechaNacimiento());
        empleadoAActualizar.setEmail(empleadoActualizado.getEmail());
        empleadoAActualizar.setTelefono(empleadoActualizado.getTelefono());
        empleadoAActualizar.setPuesto(empleadoActualizado.getPuesto());
        empleadoAActualizar.setSalario(empleadoActualizado.getSalario());

        return empleadoRepository.save(empleadoAActualizar);
    }

    public void eliminarEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }

    public List<Empleado> obtenerPorPuesto(Long puestoId) {
        return empleadoRepository.findByPuestoId(puestoId);
    }
}
