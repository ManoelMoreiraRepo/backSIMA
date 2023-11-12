package com.sima.intranet.Service;

import com.sima.intranet.Entity.Movil;
import com.sima.intranet.Interface.MovilInterface;
import com.sima.intranet.Repository.MovilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MovilSevice implements MovilInterface {
    @Autowired
    private MovilRepository movilRepository;

    @Override
    public Optional<Movil> getByDominio(String dominio) {
        return movilRepository.findByDominio(dominio);
    }

    @Override
    public void save(Movil movil) {
        movilRepository.save(movil);
    }

    @Override
    public void saveAll(List<Movil> moviles) {
        movilRepository.saveAll(moviles);
    }
}
