package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Objetivo;
import com.sima.intranet.Interface.ObjetivoInterface;
import com.sima.intranet.Service.ObjetivoServiceImpl;
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
@RequestMapping("/Objetivo")
public class ObjetivoController {
    
    @Autowired
    private ObjetivoServiceImpl iObjetivoService;
    
    @GetMapping("/traer")
    public Iterable<Objetivo> getObjetivos() {
        return iObjetivoService.getAll();
    }
    
    @PostMapping("/nuevo")
    public String createObjetivo(@RequestBody Objetivo objetivo) {
        this.iObjetivoService.newObjetivo(objetivo);
        return "El Objetivo fue creado correctamente";
    }
    
    @DeleteMapping("/detail/{id}")
    public void deleteObjetivo(@PathVariable long id) {
        iObjetivoService.deleteObjetivo(id);
    }
    
    @PutMapping("/actualizar/{id}")
    public Objetivo updateObjetivo(@RequestBody Objetivo objetivo){
    return this.iObjetivoService.modifyObjetivo(objetivo);
    }
    
    @GetMapping("/detail/{clienteId}")
    public List<Objetivo> findObjetivoByID(@PathVariable("clienteId") long id){
    return iObjetivoService.listByIdCliente(id);
    }
}
