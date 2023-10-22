package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Empleado;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
    
     List<Empleado> findByNombreEmpleadoContainingIgnoreCase(String nombreEmpleado);
    
     Optional<Empleado> findByLegajoEmpleado(String legajo);

     Optional<Empleado> findByDNIEmpleado(String dni);

     @Query("SELECT COUNT(e) , e.gerencia FROM Empleado e GROUP BY e.gerencia")
     List<String[]> countEmpleadoByGerencia();
}
