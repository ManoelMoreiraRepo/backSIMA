package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Indumentaria;
import com.sima.intranet.Service.IndumentariaServiceImpl;
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
@RequestMapping("/Indumentaria")
public class IndumentariaController {

    @Autowired
    private IndumentariaServiceImpl iIndumentariaService;

    @GetMapping("/traer")
    public Iterable<Indumentaria> getIndumentarias() {
        return iIndumentariaService.getAll();
    }
    
    @GetMapping("/traer/{id}")
    public Indumentaria getIndumentaria(@PathVariable long id){
    return iIndumentariaService.findIndumentariaById(id);
    }

    @PostMapping("/nueva")
    public String createIndumentaria(@RequestBody Indumentaria indumentaria) {
        this.iIndumentariaService.newIndumentaria(indumentaria);
        return "La indumentaria fue creado correctamente";
    }

    @DeleteMapping("/detail/{id}")
    public void deleteIndumentaria(@PathVariable long id) {
        iIndumentariaService.deleteIndumentaria(id);
    }

    @PutMapping("/actualizar/{id}")
    public Indumentaria updateIndumentaria(@RequestBody Indumentaria indumentaria) {
        return this.iIndumentariaService.modifyIndumentaria(indumentaria);
    }

    @GetMapping("/detail/{empleadoId}")
    public List<Indumentaria> findIndumentariaByIdEmpleado(@PathVariable("empleadoId") long id) {
        return iIndumentariaService.listByIdEmpleado(id);
    }
}
