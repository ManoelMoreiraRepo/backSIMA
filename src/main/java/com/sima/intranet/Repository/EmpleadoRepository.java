package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Empleado;
import java.util.List;
import java.util.Optional;

import com.sima.intranet.Enumarable.Gerencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{
     @Query("SELECT e FROM Empleado  e WHERE  LOWER(e.nombreEmpleado) LIKE %:dato% OR LOWER(e.apellidoEmpleado) LIKE %:dato%  OR e.DNIEmpleado =:dato")
     Page<Empleado> findByPorNombreyApellidoDNI(Pageable pageable , String dato);

     @Query("SELECT e FROM Empleado  e WHERE  (LOWER(e.nombreEmpleado) LIKE %:dato% OR LOWER(e.apellidoEmpleado) LIKE %:dato%  OR e.DNIEmpleado =:dato ) AND e.gerencia =:gerencia")
     Page<Empleado> findByPorNombreyApellidoDNIGerencia(Pageable pageable , String dato , Gerencia gerencia);
    
     Optional<Empleado> findByLegajoEmpleado(String legajo);

     Optional<Empleado> findByDNIEmpleado(String dni);

     @Query("SELECT COUNT(e) , e.gerencia FROM Empleado e GROUP BY e.gerencia")
     List<String[]> countEmpleadoByGerencia();

     @Query("SELECT COUNT(e) , e.sindicato FROM Empleado e GROUP BY e.sindicato")
     List<String[]> countEmpleadoBySindicato();

     @Query("SELECT e.objetivoEmpleado FROM Empleado e WHERE e.objetivoEmpleado is not null and LOWER(e.objetivoEmpleado) LIKE %:dato% GROUP BY e.objetivoEmpleado")
     Object[] getSelect2Objetivo (String dato);


     Page<Empleado> findByGerencia(Pageable pageable , Gerencia gerencia);
}
