package com.rrhh.Recursos_Humanos.Repositorios;


import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByPuestoId(Long puestoId);
    Optional<Empleado> findByDni(String dni);
    Optional<Empleado> findByEmail(String email);
    Optional<Empleado> findByTelefono(String telefono);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
}
