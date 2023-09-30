
package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Indumentaria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndumentariaRepository extends JpaRepository<Indumentaria, Long>{
    @Query("select i from Indumentaria i join i.empleado e where e.idEmpleado =:idEmpleado")
    List<Indumentaria> listByIdIndumentaria( @Param ("idEmpleado")Long idEmpleado);
}
