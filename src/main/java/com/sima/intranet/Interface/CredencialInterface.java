package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Credencial;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Enumarable.Gerencia;
import com.sima.intranet.Enumarable.Jurisdiccion;

import java.util.List;

public interface CredencialInterface {

    Credencial newCredencial(Credencial credencial);

    Iterable<Credencial> getAll();

    Credencial findCredencialByID(Long idCredencial);

    Credencial modifyCredencial(Credencial credencial);

    Boolean deleteCredencial(Long idCredencial);

    List<Credencial> listByIdEmpleado(Long idEmpleado);

    void saveAll(List<Credencial> listaCredenciales);

    Credencial findByJurisdiccionAndGerencia(Jurisdiccion jurisdiccion, Gerencia gerencia , Empleado empleado);

    void save(Credencial credencial);
}
