package com.sima.intranet.Service;

import com.sima.intranet.Entity.Infraccion;
import com.sima.intranet.Interface.InfraccionInterface;
import com.sima.intranet.Repository.InfraccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class InfraccionServicio implements InfraccionInterface {
    @Autowired
    private InfraccionRepository infraccionRepository;

    @Override
    public Optional<Infraccion> buscarPorNumeroActa(String numero) {
        return infraccionRepository.findByNumero(numero);
    }

    @Override
    public void save(Infraccion infraccion) {
        infraccionRepository.save(infraccion);
    }

    @Override
    public void saveAll(List<Infraccion> infraccionList) {
        infraccionRepository.saveAll(infraccionList);
    }
}
