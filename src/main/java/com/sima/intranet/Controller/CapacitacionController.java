package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Capacitacion;
import com.sima.intranet.Service.CapacitacionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Capacitacion")
public class CapacitacionController {
    
    @Autowired
    private CapacitacionServiceImpl iCapacitacion;
    
    @GetMapping("/traer")
    public Iterable<Capacitacion> getCapacitaciones(){
    return iCapacitacion.getAll();
    }
}
