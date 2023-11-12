package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Movil;

import java.util.List;
import java.util.Optional;

public interface MovilInterface {
    Optional<Movil> getByDominio(String dominio);
    void save(Movil movil);
    void saveAll(List<Movil> moviles);
}
