package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Credencial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial,Long> {
    @Query("select c from Credencial c join c.empleado e where e.idEmpleado =:idEmpleado")
    List<Credencial> listByIdCredencial( @Param ("idEmpleado")Long idEmpleado);
}
