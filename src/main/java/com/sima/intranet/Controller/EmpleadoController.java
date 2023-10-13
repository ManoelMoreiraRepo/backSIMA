package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Interface.EmpleadoInterface;
import com.sima.intranet.Service.EmpleadoServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoInterface iEmpleadoService;
  /*
   @GetMapping("/traer")
  public ResponseEntity<List<Empleado>> getAllEmpleados(@RequestParam(required = false) String nombre) {
    List<Empleado> empleados = new ArrayList<Empleado>();

    if (nombre == null)
      iEmpleadoService.getEmpleados().forEach(empleados::add);
    else
      iEmpleadoService.findEmpleado(nombre).forEach(empleados::add);

    if (empleados.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(empleados, HttpStatus.OK);
  }
  */
     @GetMapping("/detail/{id}")
    public Empleado getEmpleado(@PathVariable long id) {
        return iEmpleadoService.findEmpleado(id);
    } 
    
  
    @GetMapping("/traer")
    public List<Empleado> getEmpleados() {
        return iEmpleadoService.getEmpleados();
    }

    @GetMapping("/search")
    public List<Empleado> searchEmployeesByName(@RequestParam("nombreEmpleado") String nombreEmpleado) {
        return iEmpleadoService.findEmpleado(nombreEmpleado);
    }

    @PostMapping("/nuevo")
    public String createEmpleado(@RequestBody Empleado empleado) {
        iEmpleadoService.saveEmpleado(empleado);
        return "El empleado fue creado correctamente";
    }

    @DeleteMapping("/detail/{id}")
    public void deleteEmpleado(@PathVariable long id) {
        iEmpleadoService.deleteEmpleado(id);
    }

   

    @PutMapping("/actualizar/{id}")
    public String updateEmpleado(@PathVariable("id") long id, @RequestBody Empleado empleado) {
        this.iEmpleadoService.modifyEmpleado(empleado);
        return "El Empleado fue modificado correctamente";
    }

}
