package com.sima.intranet.Service;

import com.sima.intranet.Entity.Indumentaria;
import com.sima.intranet.Interface.IndumentariaInterface;
import com.sima.intranet.Repository.IndumentariaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndumentariaServiceImpl implements IndumentariaInterface {

    @Autowired
    private IndumentariaRepository rIndumentaria;

    @Override
    public Indumentaria newIndumentaria(Indumentaria indumentaria) {
        return rIndumentaria.save(indumentaria);
    }

    @Override
    public Iterable<Indumentaria> getAll() {
        return rIndumentaria.findAll();
    }

    @Override
    public Indumentaria findIndumentariaById(Long idIndumentaria) {
        Indumentaria indumentaria = rIndumentaria.findById(idIndumentaria).get();
        return indumentaria;
    }

    @Override
    public Indumentaria modifyIndumentaria(Indumentaria indumentaria) {
        Optional<Indumentaria> indumentariaEncontrada = this.rIndumentaria.findById(indumentaria.getIdIndumentaria());

        if (indumentariaEncontrada.get() != null) {
            indumentariaEncontrada.get().setNombreIndumentaria(indumentaria.getNombreIndumentaria());
            indumentariaEncontrada.get().setTipoIndumentaria(indumentaria.getTipoIndumentaria());
            indumentariaEncontrada.get().setTalleIndumentaria(indumentaria.getTipoIndumentaria());
            rIndumentaria.save(indumentaria);
        }
        return null;
    }

    @Override
    public Boolean deleteIndumentaria(Long idIndumentaria) {
        this.rIndumentaria.deleteById(idIndumentaria);
        return true;
    }

    @Override
    public List<Indumentaria> listByIdEmpleado(Long idEmpleado) {
        return this.rIndumentaria.listByIdIndumentaria(idEmpleado);
    }

}
