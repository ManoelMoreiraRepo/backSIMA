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

    @GetMapping("/objetivoSelect2")
    public FiltroSelect[] geObjetivoSelect2(@RequestParam String texto){
        FiltroSelect[] array = new FiltroSelect[1];
        array[0]= new FiltroSelect("VALOR" + texto , "VISTA" + texto);
        return array;
    }
}
