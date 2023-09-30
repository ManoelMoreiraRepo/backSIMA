package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Capacitacion;
import java.util.List;

public interface CapacitacionInterface {

    Capacitacion newCapacitacion(Capacitacion capacitacion);

    Iterable<Capacitacion> getAll();

    Capacitacion findCapacitacionByID(Long idCapacitacion);

    Capacitacion modifyCapacitacion(Capacitacion capacitacion);

    Boolean deleteCapacitacion(Long idCapacitacion);

    List<Capacitacion> listByIdEmpleado(Long idEmpleado);
}
