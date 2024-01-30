package com.sima.intranet.Service;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Entity.Indumentaria;
import com.sima.intranet.Interface.IndumentariaInterface;
import com.sima.intranet.Repository.IndumentariaRepository;

import java.time.LocalDate;
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
        Optional<Indumentaria> indumentariaEncontrada = this.rIndumentaria.findById(indumentaria.getId());

        if (indumentariaEncontrada.get() != null) {
            indumentariaEncontrada.get().setNombreIndumentaria(indumentaria.getNombreIndumentaria());
            indumentariaEncontrada.get().setFamilia(indumentaria.getFamilia());
            indumentariaEncontrada.get().setTalle(indumentaria.getTalle());
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

    @Override
    public Optional<Indumentaria> findByEmpleadoAndFechaEntrega(Empleado empleado, LocalDate fecha) {
        return this.rIndumentaria.findByEmpleadoAndFechaUltimaEntregaIndumentaria(empleado,fecha);
    }

    @Override
    public void saveAll(List<Indumentaria> indumentariaList) {
        this.rIndumentaria.saveAll(indumentariaList);
    }

    @Override
    public Optional<Indumentaria> findByEmpleadoFechaCodigoModelo(Empleado empleado, LocalDate fechaUltima, String codigo, String modelo) {
        if(empleado == null  || fechaUltima == null || codigo == null || codigo.isEmpty() || modelo == null || modelo.isEmpty()){
            return Optional.empty();
        }
        return this.rIndumentaria.findByEmpleadoAndFechaUltimaEntregaIndumentariaAndCodigoAndModeloIndumentaria(empleado , fechaUltima , codigo , modelo);
    }

}
