package com.rrhh.Recursos_Humanos.Repositorios;


import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByPuestoId(Long puestoId);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
}
