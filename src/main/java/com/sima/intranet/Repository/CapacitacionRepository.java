package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Capacitacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CapacitacionRepository extends JpaRepository<Capacitacion,Long>{
    @Query("select c from Capacitacion c join c.empleado e where e.idEmpleado =:idEmpleado")
    List<Capacitacion> listByIdCapacitacion( @Param ("idEmpleado")Long idEmpleado);
} 

