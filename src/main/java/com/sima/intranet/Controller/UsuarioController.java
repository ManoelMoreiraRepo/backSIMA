package com.sima.intranet.Controller;

import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Interface.UsuarioInterface;
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
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioInterface iusuario;

    @PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@RequestBody Usuario newUsuario){
        if(StringUtils.isBlank(newUsuario.getNombreUsuario())){
        return new ResponseEntity("El nombre es obligatorio", HttpStatus.BAD_REQUEST);
        }
        this.iusuario.newUser(newUsuario);
        /*Usuario usuario = new newUsuario(newUsuario.getNombreUsuario(),newUsuario.getCorreoUsuario());*/
        return new ResponseEntity("Usuario creado", HttpStatus.OK);
    }
    
     /*  @PostMapping("/nuevo")
    public Usuario newUsuario(@RequestBody Usuario newUsuario) {
        return this.iusuario.newUser(newUsuario);
    }*/

    @GetMapping("/mostrar")
    public Iterable<Usuario> getAll() {
        return iusuario.getAll();
    }

    @PutMapping("/actualizar/{id}")
    public Usuario updateUsuario(@RequestBody Usuario usuario) {
        return this.iusuario.modifyUser(usuario);
    }
    
    @DeleteMapping(value = "/borrar/{id}")
    public Boolean deleteUsuario(@PathVariable(value="id") int id){
    return this.iusuario.deleteUser(id);
    }

}
