
package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Indumentaria;
import java.util.List;


public interface IndumentariaInterface {
    
    Indumentaria newIndumentaria(Indumentaria indumentaria);
    
    Iterable<Indumentaria> getAll();
    
    Indumentaria findIndumentariaById(Long idIndumentaria);
    
    Indumentaria modifyIndumentaria(Indumentaria indumentaria);
    
    Boolean deleteIndumentaria(Long idIndumentaria);
    
    List<Indumentaria> listByIdEmpleado(Long idEmpleado);
}
