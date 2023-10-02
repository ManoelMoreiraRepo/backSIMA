package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Operacion;
import com.sima.intranet.Service.OperacionServiceImpl;
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
@RequestMapping("/Operacion")
public class OperacionController {

    @Autowired
    private OperacionServiceImpl iOperacion;

    @GetMapping("/traer")
    public Iterable<Operacion> getOperaciones() {
        return iOperacion.getAll();
    }

    @GetMapping("/traer/{id}")
    public Operacion getOperacion(@PathVariable long id) {
        return iOperacion.findOperacionByID(id);
    }

    @PostMapping("/nueva")
    public String createOperacion(@RequestBody Operacion operacion) {
        this.iOperacion.newObjetivo(operacion);
        return "La Grilla fue salva con Exito";
    }

    @DeleteMapping("/detail/{id}")
    public void deleteOperacion(@PathVariable long id) {
       iOperacion.deleteOperacion(id);
    }

    @PutMapping("/actualizar/{id}")
    public Operacion updateOperacion(@RequestBody Operacion operacion) {
        return this.iOperacion.modifyOperacion(operacion);
    }
    
     @GetMapping("/detail/{empleadoId}")
    public List<Operacion> findOperacionByIdEmpleado(@PathVariable("empleadoId") long id) {
        return iOperacion.listByIdCliente(id);
    }
}
