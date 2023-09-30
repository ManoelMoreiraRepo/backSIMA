package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Empleado;
import java.util.List;

public interface EmpleadoInterface {
    
    public List<Empleado> getEmpleados();
    
    public List<Empleado> findEmpleado(String nombreEmpleado);

    public void saveEmpleado(Empleado empleado);

    public void deleteEmpleado(Long idEmpleado);

    public Empleado findEmpleado(Long id);
 
    public void modifyEmpleado(Empleado empleado);

    
    
}
