package com.sima.intranet.Service;

import com.sima.intranet.Entity.Dia;
import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Interface.DiaInterface;
import com.sima.intranet.Interface.EmpleadoInterface;
import com.sima.intranet.Repository.DiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class DiaServiceImpl implements DiaInterface {
    @Autowired
    private DiaRepository diaRepository;

    @Autowired
    private EmpleadoInterface empleadoService;

    @Override
    public void save(Dia dia) {
        diaRepository.save(dia);
    }

    @Override
    public Optional<Dia> buscarPorFechaYEmpleado(LocalDate fecha, Empleado empleado) {
        return diaRepository.findByFechaAndEmpleado(fecha , empleado);
    }

    @Override
    public void saveAll(List<Dia> dias) {
        diaRepository.saveAll(dias);
    }

    @Override
    public List<Dia> buscarGrillaEmpleadoEntreFechas(LocalDate fechaInicio, LocalDate fechaFinal, Long id) {
        Empleado empleado = empleadoService.findEmpleado(id);
        return diaRepository.findByFechaBetweenAndAndEmpleado(fechaInicio,fechaFinal,empleado);
    }

    @Override
    public void delete(Dia dia) {
        diaRepository.delete(dia);
    }
}
