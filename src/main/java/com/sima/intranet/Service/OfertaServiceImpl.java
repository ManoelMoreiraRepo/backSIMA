package com.sima.intranet.Service;

import com.sima.intranet.Entity.Oferta;
import com.sima.intranet.Interface.OfertaInterface;
import com.sima.intranet.Repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class OfertaServiceImpl implements OfertaInterface {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Override
    public Page<Oferta> getOfertas(Pageable pageable) {
        return ofertaRepository.findAll(pageable);
    }

    @Override
    public Optional<Oferta> getOferta(Long id) {
        return ofertaRepository.findById(id);
    }
}
