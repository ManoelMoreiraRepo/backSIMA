package com.sima.intranet.Service;

import com.sima.intranet.Entity.Objetivo;
import com.sima.intranet.Interface.ObjetivoInterface;
import com.sima.intranet.Repository.ObjetivoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetivoServiceImpl implements ObjetivoInterface {

    @Autowired
    private ObjetivoRepository rObjetivo;

    @Override
    public Iterable<Objetivo> getAll() {
        return this.rObjetivo.findAll();
    }

    @Override
    public Objetivo newObjetivo(Objetivo objetivo) {
        return rObjetivo.save(objetivo);
    }

    @Override
    public Objetivo modifyObjetivo(Objetivo objetivo) {
        
        Optional<Objetivo> objetivoEncontrado = this.rObjetivo.findById(objetivo.getIdObjetivo());

        if (objetivoEncontrado.get() != null) {
            objetivoEncontrado.get().setNombreObjetivo(objetivo.getNombreObjetivo());
            objetivoEncontrado.get().setDireccionObjetivo(objetivo.getDireccionObjetivo());
            objetivoEncontrado.get().setCliente(objetivo.getCliente());
            rObjetivo.save(objetivo);
        }
        return null;
    }

    @Override
    public Boolean deleteObjetivo(Long idObjetivo) {
        this.rObjetivo.deleteById(idObjetivo);
        return true;
    }

    @Override
    public Objetivo findOjetivoByID(Long idObjetivo) {
        Objetivo objetivo = rObjetivo.findById(idObjetivo).get();
        return objetivo; }

    @Override
    public List<Objetivo> listByIdCliente(Long idCliente) {
        return this.rObjetivo.listByIdCliente(idCliente);
    }

    
    

}
