/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sima.intranet.Service;

import com.sima.intranet.Entity.Usuario;
import com.sima.intranet.Enumarable.ERole;
import com.sima.intranet.Interface.UsuarioInterface;
import com.sima.intranet.Repository.UsuarioRepository;
import java.util.Optional;

import com.sima.intranet.Seguridad.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class UsuarioServiceImpl implements UsuarioInterface {

    @Autowired
    private UsuarioRepository rusuario;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Usuario newUser(Usuario newUser) {
        return rusuario.save(newUser);
    }

    @Override
    public Iterable<Usuario> getAll() {
        return this.rusuario.findAll();
    }

    @Override
    public Usuario modifyUser(Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = this.rusuario.findById(usuario.getIdUsuario());
        if(usuarioEncontrado.get() != null){
        usuarioEncontrado.get().setNombreUsuario(usuario.getNombreUsuario());
        usuarioEncontrado.get().setPassword(usuario.getPassword());
        usuarioEncontrado.get().setCorreoUsuario(usuario.getCorreoUsuario());
        usuarioEncontrado.get().setRoles(usuario.getRoles());
        this.newUser(usuarioEncontrado.get());
        }
        return null;
    }

    @Override
    public Boolean deleteUser(Long idUsuario) {
        this.rusuario.deleteById(idUsuario);
        return true;
    }



}
