package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmpleadoInterface {
    
    public Page<Empleado> getEmpleados(Pageable pageable);
    
    public Page<Empleado> findEmpleado(Pageable pageable ,String nombreEmpleado);

    public void saveEmpleado(Empleado empleado);

    public void deleteEmpleado(Long idEmpleado);

    public Empleado findEmpleado(Long id);
 
    public void modifyEmpleado(Empleado empleado);

    Optional<Empleado> findByLegajo(String legajo);

    Optional<Empleado> findByDNI(String dni);


    void saveAll(List<Empleado> lista);

    List<Map<String,Object>> getCantidadNominaPorGerencia();

    List<Map<String,Object>> getCantidadNominaPorSindicado();
}
