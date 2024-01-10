package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Capacitacion;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.CursoHabilitante;

import java.util.List;
import java.util.Optional;

public interface CapacitacionInterface {

    Capacitacion newCapacitacion(Capacitacion capacitacion);

    Iterable<Capacitacion> getAll();

    Capacitacion findCapacitacionByID(Long idCapacitacion);

    Capacitacion modifyCapacitacion(Capacitacion capacitacion);

    Boolean deleteCapacitacion(Long idCapacitacion);

    List<Capacitacion> listByIdEmpleado(Long idEmpleado);

    Optional<Capacitacion> findByEmpleadoAndTipoCursoHabilitante(Empleado empleado , CursoHabilitante tipoCurso);

    void saveAll(List<Capacitacion> capacitacions);
}
