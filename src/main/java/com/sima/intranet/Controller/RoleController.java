package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Role;
import com.sima.intranet.Interface.RoleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Role")
public class RoleController {

    @Autowired
    private RoleInterface irole;

    @PostMapping("/nuevo")
    public Role newRole(@RequestBody Role newRole) {
        return this.irole.newRole(newRole);
    }

    @GetMapping("/mostrar")
    public Iterable<Role> getAll() {
        return irole.gettAll();
    }

    @PostMapping("/actualizar")
    public Role updateRole(@RequestBody Role role){
    return this.irole.modyfyRole(role);
    }
    
    
}
