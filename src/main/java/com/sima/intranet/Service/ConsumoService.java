package com.sima.intranet.Service;

import com.sima.intranet.Entity.Consumo;
import com.sima.intranet.Entity.Movil;
import com.sima.intranet.Repository.ConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ConsumoService {

    @Autowired
    private ConsumoRepository consumoRepository;


    public Optional<Consumo> findByMovilAndFecha(Movil movil , LocalDateTime fecha){
        return consumoRepository.findByMovilAndFecha(movil,fecha);
    }

    public void save(Consumo consumo){
        consumoRepository.save(consumo);
    }

    public void saveAll(List<Consumo> list){
        consumoRepository.saveAll(list);
    }

    public Object[] getTotalesPorGerencia() {
        return consumoRepository.getTotalesPorGerencia();
    }

    public Object[] getTotalesPorMovil() {
        return consumoRepository.getTotalPorDominio();
    }
}
