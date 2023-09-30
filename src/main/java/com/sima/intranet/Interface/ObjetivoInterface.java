package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Objetivo;
import java.util.List;

public interface ObjetivoInterface {

    Objetivo newObjetivo(Objetivo objetivo);

    Iterable<Objetivo> getAll();
    
    Objetivo findOjetivoByID(Long idObjetivo);

    Objetivo modifyObjetivo(Objetivo objetivo);

    Boolean deleteObjetivo(Long idObjetivo);
    
    List<Objetivo> listByIdCliente(Long idCliente);

}
