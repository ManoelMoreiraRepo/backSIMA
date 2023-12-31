package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Credencial;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Jurisdiccion;
import com.sima.intranet.Enumarable.TipoCredencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial,Long> {
    @Query("select c from Credencial c join c.empleado e where e.idEmpleado =:idEmpleado")
    List<Credencial> listByIdCredencial( @Param ("idEmpleado")Long idEmpleado);

    Credencial findByJurisdiccionAndGerenciaAndEmpleado(Jurisdiccion jurisdiccion , Gerencia gerencia , Empleado empleado);

    Long countCredencialByFechaVencimentoCredencialBeforeAndJurisdiccionAndGerenciaAndTipo(LocalDate fecha , Jurisdiccion jurisdiccion , Gerencia gerencia , TipoCredencial tipo);

    Long countCredencialByFechaVencimentoCredencialBetweenAndJurisdiccionAndGerenciaAndTipo(LocalDate fecha1 , LocalDate fecha2 , Jurisdiccion jurisdiccion ,Gerencia gerencia , TipoCredencial tipo);

}
