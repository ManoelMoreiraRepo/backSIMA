package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Oferta;
import com.sima.intranet.Enumarable.Gerencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OfertaInterface {
    Page<Oferta> getOfertas(Pageable pageable);

    Optional<Oferta> getOferta(Long id);

    Optional<Oferta> getOferta(String codigo , Gerencia gerencia);

    void saveAll(List<Oferta> lista);
}
