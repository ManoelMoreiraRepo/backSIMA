package com.sima.intranet.Service;

import com.sima.intranet.Entity.Operacion;
import com.sima.intranet.Interface.OperacionInterface;
import com.sima.intranet.Repository.OperacionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperacionServiceImpl implements OperacionInterface {

    @Autowired
    private OperacionRepository rOperacion;

    @Override
    public Operacion newObjetivo(Operacion operacion) {
        return rOperacion.save(operacion);
    }

    @Override
    public Iterable<Operacion> getAll() {
        return this.rOperacion.findAll();
    }

    @Override
    public Operacion findOperacionByID(Long idOperacion) {
        Operacion operacionEncontrada = rOperacion.findById(idOperacion).get();
        return operacionEncontrada;
    }

    @Override
    public Operacion modifyOperacion(Operacion operacion) {
        Optional<Operacion> operacionEncontrada = this.rOperacion.findById(operacion.getIdOperacion());

        if (operacionEncontrada.get() != null) {
            operacionEncontrada.get().setGrillaOperacion(operacion.getGrillaOperacion());
            operacionEncontrada.get().setFechaInicioOperacion(operacion.getFechaInicioOperacion());
            operacionEncontrada.get().setFechaFinalOperacion(operacion.getFechaFinalOperacion());
            rOperacion.save(operacion);
        }
        return null;
    }

    @Override
    public Boolean deleteOperacion(Long idOperacion) {
        this.rOperacion.deleteById(idOperacion);
        return true;
    }

    @Override
    public List<Operacion> listByIdCliente(Long idCliente) {
        return this.rOperacion.listByIdCliente(idCliente);
    }
}
