
package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Entity.Indumentaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IndumentariaRepository extends JpaRepository<Indumentaria, Long>{
    @Query("select i from Indumentaria i join i.empleado e where e.idEmpleado =:idEmpleado")
    List<Indumentaria> listByIdIndumentaria( @Param ("idEmpleado")Long idEmpleado);

    Optional<Indumentaria> findByEmpleadoAndFechaUltimaEntregaIndumentaria(Empleado empleado , LocalDate fechaUltimaEntregaIndumentaria);

    Optional<Indumentaria> findByEmpleadoAndFechaUltimaEntregaIndumentariaAndCodigoAndModeloIndumentaria(Empleado empleado , LocalDate fechaUltimaEntregaIndumentaria , String codigo , String  modeloIndumentaria);
    @Query("SELECT i.familia FROM Indumentaria i WHERE i.familia is not null AND LOWER(i.familia) LIKE %:dato% GROUP BY i.familia")
    Object[] getSelect2Familia(String dato);

    @Query("SELECT i.nombreIndumentaria FROM Indumentaria i WHERE i.nombreIndumentaria is not null AND LOWER(i.nombreIndumentaria) LIKE %:dato% GROUP BY i.nombreIndumentaria")
    Object[] getSelect2Producto(String dato);

    @Query("SELECT i.modeloIndumentaria FROM Indumentaria i WHERE i.modeloIndumentaria is not null AND LOWER(i.modeloIndumentaria) LIKE %:dato% GROUP BY i.modeloIndumentaria")
    Object[] getSelect2Modelo(String dato);
}
