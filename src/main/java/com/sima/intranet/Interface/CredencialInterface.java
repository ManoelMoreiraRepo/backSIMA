package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Credencial;
import java.util.List;

public interface CredencialInterface {

    Credencial newCredencial(Credencial credencial);

    Iterable<Credencial> getAll();

    Credencial findCredencialByID(Long idCredencial);

    Credencial modifyCredencial(Credencial credencial);

    Boolean deleteCredencial(Long idCredencial);

    List<Credencial> listByIdEmpleado(Long idEmpleado);

    void saveAll(List<Credencial> listaCredenciales);
}
