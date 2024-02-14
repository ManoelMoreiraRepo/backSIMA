package com.sima.intranet.Controller;

import com.sima.intranet.Dto.MessageResponse;
import com.sima.intranet.Dto.SignupRequest;
import com.sima.intranet.Entity.Mensaje;
import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Exception.ParametroInvalidoException;
import com.sima.intranet.Interface.UsuarioInterface;
import com.sima.intranet.Repository.UsuarioRepository;
import com.sima.intranet.Service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioInterface iusuario;

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioRepository usuarioRepository;


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

    @GetMapping("/traer/{id}")
    public ResponseEntity<Usuario> getUsuarioByID(@PathVariable Long id){
        Optional<Usuario> usuario = this.iusuario.getUsuario(id);
        if(usuario.isEmpty()){
            throw new ParametroInvalidoException("Usuario no encontrado.");
        }
        return ResponseEntity.ok().body(usuario.get());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<MessageResponse> updateUsuario(@RequestBody SignupRequest signUpRequest){
        Usuario user = authService.registrarOActualizarUsuario(signUpRequest);
        if(user.getIdUsuario()!= 0L){
         return ResponseEntity.ok().body(new MessageResponse("Usuario registrado correctamente."));
        }else{
            return  ResponseEntity.badRequest().body(new MessageResponse("Ocurrio un error al procesar el usuario."));
        }
    }

    @PutMapping("/actualizar/{id}")
    public Usuario updateUsuario(@RequestBody Usuario usuario) {
        return this.iusuario.modifyUser(usuario);
    }
    
    @DeleteMapping(value = "/borrar/{id}")
    public Boolean deleteUsuario(@PathVariable(value="id") Long id){
    return this.iusuario.deleteUser(id);
    }

}
