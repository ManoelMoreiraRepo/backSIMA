package com.sima.intranet.Controller;

import com.sima.intranet.Dto.Filtro;
import com.sima.intranet.Dto.FiltroSelect;
import com.sima.intranet.Enumarable.TipoFiltro;
import com.sima.intranet.Service.FiltroServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filtro")
public class FiltroController {

    @Autowired
    private FiltroServiceImpl filtroService;

    @GetMapping("/select")
    public Filtro getFiltroSelect(@RequestParam TipoFiltro tipo , @RequestParam Boolean seleccione){
        return filtroService.procesarFiltroSelect(tipo , seleccione);
    }

    @GetMapping("/select2")
    public FiltroSelect[] geObjetivoSelect2(@RequestParam String texto , @RequestParam TipoFiltro tipo){
        FiltroSelect[] array = new FiltroSelect[0];
        switch (tipo){
            case OBJETIVO :
                array = filtroService.getSelect2Objetivo(texto);
                break;
            case FAMILIA:
                array = filtroService.getSelect2Familia(texto);
                break;
            case PRODUCTO:
                array = filtroService.getSelect2Producto(texto);
                break;
            case MODELO:
                array = filtroService.getSelect2Modelo(texto);
                break;
        }
        return array;
    }
}
