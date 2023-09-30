package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Capacitacion;
import com.sima.intranet.Service.CapacitacionServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Capacitacion")
public class CapacitacionController {

    @Autowired
    private CapacitacionServiceImpl iCapacitacion;

    @GetMapping("/traer")
    public Iterable<Capacitacion> getCapacitaciones() {
        return iCapacitacion.getAll();
    }

    @GetMapping("/traer/{id}")
    public Capacitacion getCapacitacion(@PathVariable long id) {
        return iCapacitacion.findCapacitacionByID(id);
    }

    @PostMapping("/nueva")
    public String createCapacitacion(@RequestBody Capacitacion capacitacion) {
        this.iCapacitacion.newCapacitacion(capacitacion);
        return "La Capacitacion fue creada correctamente";
    }

    @DeleteMapping("/detail/{id}")
    public String deleteCapacitacion(@PathVariable long id) {
        this.iCapacitacion.deleteCapacitacion(id);
        return "La Capacitacion fue eliminada";
    }

    @PutMapping("/actualizar/{id}")
    public Capacitacion updateCapacitacion(@RequestBody Capacitacion capacitacion) {
        return iCapacitacion.modifyCapacitacion(capacitacion);
    }

    @GetMapping("/detail/{idEmpleado}")
    public List<Capacitacion> findCapacitacionByIdEmpleado(@PathVariable("empleadoId") long id) {
        return iCapacitacion.listByIdEmpleado(id);
    }

}
