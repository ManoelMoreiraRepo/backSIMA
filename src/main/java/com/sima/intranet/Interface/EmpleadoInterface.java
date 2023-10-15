package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Empleado;
import java.util.List;
import java.util.Optional;

public interface EmpleadoInterface {
    
    public List<Empleado> getEmpleados();
    
    public List<Empleado> findEmpleado(String nombreEmpleado);

    public void saveEmpleado(Empleado empleado);

    public void deleteEmpleado(Long idEmpleado);

    public Empleado findEmpleado(Long id);
 
    public void modifyEmpleado(Empleado empleado);

    public Optional<Empleado> findByLegajo(String legajo);


    void saveAll(List<Empleado> lista);
}
