package com.sima.intranet.Controller;

import com.sima.intranet.Interface.InfraccionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dpa")
public class InfraccionController {
    @Autowired
    private InfraccionInterface infraccionService;

    @GetMapping("/estadistica")
    public Map getEstadistica(){
        return infraccionService.getEstadisticas();
    }
}
