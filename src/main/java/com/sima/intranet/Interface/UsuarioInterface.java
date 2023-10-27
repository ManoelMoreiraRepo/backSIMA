package com.sima.intranet.Interface;

import com.sima.intranet.Entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;

public interface UsuarioInterface {
     /*Nuevo usuario*/
    Usuario newUser(Usuario newUser);
    /*Lista Usuario*/
    Iterable<Usuario> getAll();
    /*Modifica Usuario*/
    Usuario modifyUser(Usuario usuario);
    /*Elimina Usuario*/
    Boolean deleteUser(Long idUsuario);

 }
