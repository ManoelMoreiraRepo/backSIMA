package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Operacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OperacionRepository extends JpaRepository<Operacion, Long>{
    @Query("select o from Objetivo o join o.cliente c where c.idCliente = :idCliente")
    List<Operacion> listByIdCliente( @Param ("idCliente")Long idCliente);
}