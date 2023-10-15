package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Empleado;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
    
     List<Empleado> findByNombreEmpleadoContainingIgnoreCase(String nombreEmpleado);
    
     Optional<Empleado> findByLegajoEmpleado(String legajo);
}
