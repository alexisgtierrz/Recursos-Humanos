package com.rrhh.Recursos_Humanos.Servicios;

import com.rrhh.Recursos_Humanos.Modelos.Denuncia;
import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Repositorios.DenunciaRepository;
import com.rrhh.Recursos_Humanos.Repositorios.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DenunciaService {

    @Autowired
    public DenunciaRepository denunciaRepository;

    @Autowired
    public EmpleadoRepository empleadoRepositorio;

    // Crear y asignar denuncia a un empleado
    public Denuncia crearDenuncia(Long empleadoId, Denuncia denuncia) {
        Optional<Empleado> empleadoOpt = empleadoRepositorio.findById(empleadoId);

        if (empleadoOpt.isPresent()) {
            Empleado empleado = empleadoOpt.get();
            empleado.addDenuncia(denuncia);
            return denunciaRepository.save(denuncia);
        } else {
            throw new RuntimeException("Empleado no encontrado con ID: " + empleadoId);
        }
    }

    // Listar todas las denuncias de un empleado
    public List<Denuncia> obtenerDenunciasPorEmpleado(Long empleadoId) {
        return denunciaRepository.findByEmpleadoId(empleadoId);
    }

    // Eliminar denuncia
    public void eliminarDenuncia(Long id) {
        denunciaRepository.deleteById(id);
    }

    public List<Denuncia> obtenerTodas() {
        return denunciaRepository.findAll();
    }

    public Denuncia guardar(Denuncia denuncia) {
        return denunciaRepository.save(denuncia);
    }
}
