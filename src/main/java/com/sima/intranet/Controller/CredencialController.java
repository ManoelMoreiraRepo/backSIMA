package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Credencial;
import com.sima.intranet.Service.CredencialServiceImpl;
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
@RequestMapping("/Credencial")
public class CredencialController {

    @Autowired
    private CredencialServiceImpl iCredencial;

    @GetMapping("/traer")
    public Iterable<Credencial> getCredenciales() {
        return iCredencial.getAll();
    }

    @GetMapping("/traer/{id}")
    public Credencial getCredencial(@PathVariable long id) {
        return iCredencial.findCredencialByID(id);
    }

    @PostMapping("/nueva")
    public String createCredencial(@RequestBody Credencial credencial) {
        this.iCredencial.newCredencial(credencial);
        return "La Credencial fue creada correctamente";
    }

    @DeleteMapping
    public void deleteCredencial(@PathVariable long id) {
        iCredencial.deleteCredencial(id);

    }

    @PutMapping("/actualizar/{id}")
    public Credencial updateCredencial(@RequestBody Credencial credencial) {
        return this.iCredencial.modifyCredencial(credencial);
    }

    @GetMapping("/detail/{empleadoId}")
    public List<Credencial> findCredencialByIdEmpleado(@PathVariable("empleadoId") long id) {
        return iCredencial.listByIdEmpleado(id);
    }
}
