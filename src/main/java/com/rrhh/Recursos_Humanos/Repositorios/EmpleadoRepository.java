package com.rrhh.Recursos_Humanos.Repositorios;


import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
