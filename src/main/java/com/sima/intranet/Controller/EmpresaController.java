package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Empresa;
import com.sima.intranet.Interface.EmpresaInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Empresa")
public class EmpresaController {
    
    @Autowired
    private EmpresaInterface iEmpresa;
    
    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@RequestBody Empresa newEmpresa) {
        if (StringUtils.isBlank(newEmpresa.getNombreEmpresa())) {
            return new ResponseEntity("El nombre es obligatorio", HttpStatus.BAD_REQUEST);
        }
        this.iEmpresa.newEmpresa(newEmpresa);
        return new ResponseEntity("Empresa Creada", HttpStatus.OK);
    }

    @GetMapping("/mostrar")
    public Iterable<Empresa> getAll() {
        return this.iEmpresa.getAll();
    }

    @PutMapping("/actualizar/{id}")
    public Empresa updateEmpresa(@RequestBody Empresa empresa) {
        return this.iEmpresa.modifyEmpresa(empresa);
    }

    @DeleteMapping(value = "/borrar/{id}")
    public Boolean deleteEmpresa(@PathVariable(value = "id") Long id) {
        return this.iEmpresa.deleteEmpresa(id);
    }
}
