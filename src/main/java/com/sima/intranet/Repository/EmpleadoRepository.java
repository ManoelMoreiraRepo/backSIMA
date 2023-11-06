package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Empleado;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
     @Query("SELECT e FROM Empleado  e WHERE  LOWER(e.nombreEmpleado) LIKE %:dato% OR LOWER(e.apellidoEmpleado) LIKE %:dato% ")
     Page<Empleado> findByPorNombreyApellido(Pageable pageable , String dato);
    
     Optional<Empleado> findByLegajoEmpleado(String legajo);

     Optional<Empleado> findByDNIEmpleado(String dni);

     @Query("SELECT COUNT(e) , e.gerencia FROM Empleado e GROUP BY e.gerencia")
     List<String[]> countEmpleadoByGerencia();

     @Query("SELECT COUNT(e) , e.sindicato FROM Empleado e GROUP BY e.sindicato")
     List<String[]> countEmpleadoBySindicato();
}
