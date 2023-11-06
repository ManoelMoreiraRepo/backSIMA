package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Empleado;
import com.sima.intranet.Interface.EmpleadoInterface;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public Page<Empleado> getEmpleados(@PageableDefault(size = 20 ) Pageable pageable) {
        return iEmpleadoService.getEmpleados(pageable);
    }

    @GetMapping("/search")
    public Page<Empleado> searchEmployeesByName(@PageableDefault(size = 20 ) Pageable pageable, @RequestParam("nombreEmpleado")String nombreEmpleado , @RequestParam String ordenado , @RequestParam String orden ) {
        if(!ordenado.isEmpty() && !orden.isEmpty()){
            if(orden.equals("ASC")){
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, ordenado));
            }else{
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, ordenado));
            }

        }

         if(nombreEmpleado.isBlank()){
            return iEmpleadoService.getEmpleados(pageable);
        }

        return iEmpleadoService.findEmpleado(pageable , nombreEmpleado.trim().toLowerCase());
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

    @GetMapping("/cantidadNominaEmpresa")
    public List<Map<String,Object>> getCantidadNominaPorEmpresa(){
        return iEmpleadoService.getCantidadNominaPorGerencia();
    }

    @GetMapping("/cantidadNominaSindicato")
    public List<Map<String,Object>> getCantidadNominaPorSindicato(){
        return iEmpleadoService.getCantidadNominaPorSindicado();
    }


}
