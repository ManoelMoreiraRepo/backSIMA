package com.sima.intranet.Repository;

import com.sima.intranet.Entity.Dia;
import com.sima.intranet.Entity.Empleado;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface DiaRepository extends JpaRepository<Dia,Long> {
    Optional<Dia> findByFechaAndEmpleado(LocalDate fecha , Empleado empleado);

    List<Dia> findByFechaBetweenAndAndEmpleado(LocalDate fechaInicio , LocalDate fechaFin , Empleado empleado);
}
