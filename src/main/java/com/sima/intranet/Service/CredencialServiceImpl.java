package com.sima.intranet.Service;

import com.sima.intranet.Entity.Credencial;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Jurisdiccion;
import com.sima.intranet.Enumarable.TipoCredencial;
import com.sima.intranet.Interface.CredencialInterface;
import com.sima.intranet.Repository.CredencialRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredencialServiceImpl implements CredencialInterface {

    @Autowired
    private CredencialRepository rCredencial;

    @Override
    public Credencial newCredencial(Credencial credencial) {
        return rCredencial.save(credencial);

    }

    @Override
    public Iterable<Credencial> getAll() {
        return this.rCredencial.findAll();
    }

    @Override
    public Credencial findCredencialByID(Long idCredencial) {
        Credencial credencialEncontrada = rCredencial.findById(idCredencial).get();
        return credencialEncontrada;
    }

    @Override
    public Credencial modifyCredencial(Credencial credencial) {
        Optional<Credencial> credencialEncontrada = this.rCredencial.findById(credencial.getIdCredencial());
        if (credencialEncontrada != null) {
            credencialEncontrada.get().setCodigoCredencial(credencial.getCodigoCredencial());
            credencialEncontrada.get().setNombreCredencial(credencial.getNombreCredencial());
            credencialEncontrada.get().setEstadoCredencial(credencial.isEstadoCredencial());
            credencialEncontrada.get().setFechaOtorgamentoCredencial(credencial.getFechaOtorgamentoCredencial());
            credencialEncontrada.get().setFechaVencimentoCredencial(credencial.getFechaVencimentoCredencial());

            this.rCredencial.save(credencial);
        }
        return null;
    }

    @Override
    public Boolean deleteCredencial(Long idCredencial) {
        this.rCredencial.deleteById(idCredencial);
        return true;
    }

    @Override
    public List<Credencial> listByIdEmpleado(Long idEmpleado) {
        return this.rCredencial.listByIdCredencial(idEmpleado);
    }

    @Override
    public void saveAll(List<Credencial> listaCredenciales) {
        this.rCredencial.saveAll(listaCredenciales);
    }

    @Override
    public Credencial findByJurisdiccionAndGerencia(Jurisdiccion jurisdiccion, Gerencia gerencia , Empleado empleado) {
        return this.rCredencial.findByJurisdiccionAndGerenciaAndEmpleado(jurisdiccion,gerencia , empleado);
    }

    @Override
    public void save(Credencial credencial) {
        this.rCredencial.save(credencial);
    }

    public Long getCantidadCredencialesConVencimientoAnteriorA(LocalDate fecha , Jurisdiccion jurisdiccion , Gerencia gerencia , TipoCredencial tipo){
        return this.rCredencial.countCredencialByFechaVencimentoCredencialBeforeAndJurisdiccionAndGerenciaAndTipo(fecha,jurisdiccion,gerencia , tipo);
    }

    public Long getCantidadCredencialesConVecimientoEntre(LocalDate fecha1, LocalDate fecha2 , Jurisdiccion jurisdiccion , Gerencia gerencia , TipoCredencial tipo){
        return this.rCredencial.countCredencialByFechaVencimentoCredencialBetweenAndJurisdiccionAndGerenciaAndTipo(fecha1,fecha2,jurisdiccion,gerencia , tipo);
    }
}
