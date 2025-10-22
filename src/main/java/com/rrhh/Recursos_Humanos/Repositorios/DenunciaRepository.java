package com.rrhh.Recursos_Humanos.Repositorios;

import com.rrhh.Recursos_Humanos.Modelos.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findByEmpleadoId(Long empleadoId);
}
