package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Operacion;
import java.util.List;

public interface OperacionInterface {
    
    Operacion newObjetivo(Operacion operacion);

    Iterable<Operacion> getAll();
    
    Operacion findOperacionByID(Long idOperacion);

    Operacion modifyOperacion(Operacion operacion);

    Boolean deleteOperacion(Long idOperacion);
    
    List<Operacion> listByIdCliente(Long idCliente);
}
