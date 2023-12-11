package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Dia;
import com.sima.intranet.Entity.Empleado;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaInterface {
    void save(Dia dia);
    Optional<Dia> buscarPorFechaYEmpleado(LocalDate fecha , Empleado empleado);

    void saveAll(List<Dia> dias);

    List<Dia> buscarGrillaEmpleadoEntreFechas(LocalDate fechaInicio, LocalDate fechaFinal, Long id);

    void delete(Dia dia);
}
