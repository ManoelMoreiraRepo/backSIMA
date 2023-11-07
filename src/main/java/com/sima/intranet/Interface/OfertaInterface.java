package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Oferta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OfertaInterface {
    Page<Oferta> getOfertas(Pageable pageable);

    Optional<Oferta> getOferta(Long id);
}
