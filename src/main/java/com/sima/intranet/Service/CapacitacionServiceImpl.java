package com.sima.intranet.Service;

import com.sima.intranet.Entity.Capacitacion;
import com.sima.intranet.Interface.CapacitacionInterface;
import com.sima.intranet.Repository.CapacitacionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CapacitacionServiceImpl implements CapacitacionInterface{
    
    @Autowired
    private CapacitacionRepository rCapacitacion;
    
    @Override
    public Capacitacion newCapacitacion(Capacitacion capacitacion) {
        return rCapacitacion.save(capacitacion);
    }

    @Override
    public Iterable<Capacitacion> getAll() {
        return rCapacitacion.findAll();
    }

    @Override
    public Capacitacion findCapacitacionByID(Long idCapacitacion) {
        
        Capacitacion capacitacionEncontrada= rCapacitacion.findById(idCapacitacion).get();
        return capacitacionEncontrada;
    }

    @Override
    public Capacitacion modifyCapacitacion(Capacitacion capacitacion) {
        Optional<Capacitacion> capacitacionEncontrada=this.rCapacitacion.findById(capacitacion.getIdCapacitacion());
        if (capacitacionEncontrada.get() != null) {
            capacitacionEncontrada.get().setNombreCapacitacion(capacitacion.getNombreCapacitacion());
            capacitacionEncontrada.get().setFechaOtorgamentoCapacitacion(capacitacion.getFechaOtorgamentoCapacitacion());
            capacitacionEncontrada.get().setFechaVencimentoCapacitacion(capacitacion.getFechaVencimentoCapacitacion());
            capacitacionEncontrada.get().setEstadoCapacitacion(capacitacion.isEstadoCapacitacion());
            rCapacitacion.save(capacitacion);
          }
        return null;
    }

    @Override
    public Boolean deleteCapacitacion(Long idCapacitacion) {
        this.rCapacitacion.deleteById(idCapacitacion);
        return true;
    }

    @Override
    public List<Capacitacion> listByIdEmpleado(Long idEmpleado) {
        return this.rCapacitacion.listByIdCapacitacion(idEmpleado);
    }

   
    
}
