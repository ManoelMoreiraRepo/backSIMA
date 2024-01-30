
package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Entity.Indumentaria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface IndumentariaInterface {
    
    Indumentaria newIndumentaria(Indumentaria indumentaria);
    
    Iterable<Indumentaria> getAll();
    
    Indumentaria findIndumentariaById(Long idIndumentaria);
    
    Indumentaria modifyIndumentaria(Indumentaria indumentaria);
    
    Boolean deleteIndumentaria(Long idIndumentaria);
    
    List<Indumentaria> listByIdEmpleado(Long idEmpleado);

    Optional<Indumentaria> findByEmpleadoAndFechaEntrega(Empleado empleado , LocalDate fecha);

    void saveAll(List<Indumentaria> indumentariaList);

    Optional<Indumentaria> findByEmpleadoFechaCodigoModelo(Empleado empleado, LocalDate fechaUltima, String codigo, String modelo);
}
