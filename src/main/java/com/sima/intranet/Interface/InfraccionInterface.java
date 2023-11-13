package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Infraccion;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface InfraccionInterface {
    Optional<Infraccion> buscarPorNumeroActa(String numero);

    void save(Infraccion infraccion);

    void saveAll(List<Infraccion> infraccionList);

    Map getEstadisticas();
}
