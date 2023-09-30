
package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Objetivo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObjetivoRepository extends JpaRepository<Objetivo, Long>{
    @Query("select o from Objetivo o join o.cliente c where c.idCliente = :idCliente")
    List<Objetivo> listByIdCliente( @Param ("idCliente")Long idCliente);
}
