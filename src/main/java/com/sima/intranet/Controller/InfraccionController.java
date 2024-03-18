package com.sima.intranet.Controller;

import com.sima.intranet.Interface.InfraccionInterface;
import com.sima.intranet.Service.ConsumoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dpa")
public class InfraccionController {
    @Autowired
    private InfraccionInterface infraccionService;

    @Autowired
    private ConsumoService consumoService;

    @GetMapping("/estadistica")
    public Map getEstadistica(){
        return infraccionService.getEstadisticas();
    }

    @GetMapping("/activos")
    public List<Long> getPorGenerenciaActivos(){
        return infraccionService.getActivosPorGerencia();
    }

    @GetMapping("/totales")
    public Object[] getTotalesConsumo(@RequestParam  @NotNull String agrupado){
        Object[] data = new Object[0];
        if(agrupado.equals("GERENCIA")){
            data = consumoService.getTotalesPorGerencia();
        }else if ( agrupado.equals("MOVIL") ){
            data = consumoService.getTotalesPorMovil();
        }
        return data;
    }
}
